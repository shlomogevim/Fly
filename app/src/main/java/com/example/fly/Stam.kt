package com.example.fly

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.ar.core.Anchor
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.assets.RenderableSource
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode

class Stam(val context: Context) {
    private lateinit var arFragment: ArFragment

/*


private fun spawnObject(anchor: Anchor,modelUri: Uri){
        val rendrebaleSource= RenderableSource.builder()
            .setSource(this,modelUri,RenderableSource.SourceType.GLB)
            .setScale(0.002f)
            .setRecenterMode(RenderableSource.RecenterMode.ROOT)
            .build()

        ModelRenderable.builder()
            .setSource(this,rendrebaleSource)
            .setRegistryId(modelUri)
            .build()
            .thenAccept {
                addNodeToScene(anchor,it)
            }.exceptionally {
                Log.e("clima","Somthing go wrong in loading model")
                null
            }
    }

    private fun addNodeToScene(anchor: Anchor,modelRenderable: ModelRenderable){
        val anchorNode=AnchorNode(anchor)
        TransformableNode(arFragment.transformationSystem).apply {
            renderable=modelRenderable
            setParent(anchorNode)
        }
        arFragment.arSceneView.scene.addChild(anchorNode)
    }


* */



}