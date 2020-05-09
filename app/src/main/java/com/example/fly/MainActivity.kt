package com.example.fly

import android.media.CamcorderProfile
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.Toast
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.Node
import com.google.ar.sceneform.animation.ModelAnimator
import com.google.ar.sceneform.math.Quaternion
import com.google.ar.sceneform.math.Vector3
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val spaceship =Models.Fly
   private val modelResourceIds = R.raw.fly5

    private lateinit var arFragment: ArFragment
    private var curCameraPosition = Vector3.zero()
    private val nodes = mutableListOf<RotatingNode>()
    private lateinit var photoSaver: PhotoSaver
    private lateinit var videoRecorder: VideoRecorder
    private var isRecording = false
    private val url1="https://firebasestorage.googleapis.com/v0/b/thermal-proton-239415.appspot.com/o/out.glb?alt=media&token=1a1bca0f-143e-446e-bbb8-1b49700bcdb8"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        arFragment = fragment as ArFragment

        arFragment.setOnTapArPlaneListener { hitResult, _, _ ->
            loadModelAndAddToScene(hitResult.createAnchor(), modelResourceIds)
        }
        arFragment.arSceneView.scene.addOnUpdateListener {
            updateNodes()
        }
        setupFab()
    }

    private fun setupFab() {
        photoSaver = PhotoSaver(this)
        videoRecorder = VideoRecorder(this).apply {
            sceneView = arFragment.arSceneView

            setVideoQuality(CamcorderProfile.QUALITY_1080P, resources.configuration.orientation)
        }
        fab.setOnClickListener {
            if (!isRecording) {
                photoSaver.takePhoto(arFragment.arSceneView)
            }
        }

        fab.setOnLongClickListener {
            isRecording = videoRecorder.toggleRecordingState()
            true
        }

        fab.setOnTouchListener { view, motionEvent ->

            if (motionEvent.action == MotionEvent.ACTION_UP && isRecording) {

                isRecording = videoRecorder.toggleRecordingState()

                Toast.makeText(this, "Saved video to gallery!", Toast.LENGTH_LONG).show()

                true

            } else false

        }

    }
    private fun updateNodes() {
        curCameraPosition = arFragment.arSceneView.scene.camera.worldPosition
        for(node in nodes) {
            node.worldPosition = Vector3(curCameraPosition.x, node.worldPosition.y, curCameraPosition.z)
        }
    }

    private fun loadModelAndAddToScene(anchor: Anchor, modelResourceId: Int) {
        ModelRenderable.builder()
            .setSource(this, modelResourceId)
            .build()
            .thenAccept { modelRenderable ->


                addNodeToScene(anchor, modelRenderable, spaceship)
                eliminateDot()
            }.exceptionally {
                Toast.makeText(this, "Error creating node: $it", Toast.LENGTH_LONG).show()
                null
            }
    }

    private fun addNodeToScene(anchor: Anchor, modelRenderable: ModelRenderable, spaceship: Models) {
        val anchorNode = AnchorNode(anchor)
        val rotatingNode = RotatingNode(spaceship.degreesPerSecond).apply {
            setParent(anchorNode)
        }
        Node().apply {
            renderable = modelRenderable
            setParent(rotatingNode)
            localPosition = Vector3(spaceship.radius, spaceship.height, 0f)
            localRotation = Quaternion.eulerAngles(Vector3(0f, spaceship.rotationDegrees, 0f))
        }
        arFragment.arSceneView.scene.addChild(anchorNode)
        nodes.add(rotatingNode)
        val animationData = modelRenderable.getAnimationData("Beedrill_Animation")
        ModelAnimator(animationData, modelRenderable).apply {
            repeatCount = ModelAnimator.INFINITE
            start()
        }

    }

    private fun eliminateDot(){
        arFragment.arSceneView.planeRenderer.isVisible = false
        arFragment.planeDiscoveryController.hide()
        arFragment.planeDiscoveryController.setInstructionView(null)
    }
}
