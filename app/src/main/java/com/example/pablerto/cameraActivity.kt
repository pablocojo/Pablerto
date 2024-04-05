package com.example.pablerto
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.Task
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.Text
import com.google.mlkit.vision.text.TextRecognition
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import com.google.mlkit.vision.text.TextRecognizerOptionsInterface
import java.util.*

class cameraActivity : AppCompatActivity() {
    private val REQUEST_IMAGE_CAPTURE = 101
    private lateinit var currentPhotoPath: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: UserImagesAdapter
    private lateinit var currentUser: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        val button: Button = findViewById(R.id.btnCreateReto)
        val tvUser: TextView = findViewById(R.id.textView4)
        recyclerView = findViewById(R.id.recycler_view_horizontal_persons_who_mabe_you_know)

        // Verifica si el usuario se proporcionó como extra en el Intent
        if (currentuser.currentUser != null) {
            currentUser = currentuser.currentUser!!
            tvUser.text = "Fotos de ${currentUser.name}"

        } else {
            Log.e("cameraActivity", "Error: currentUser es null")
            return
        }

        Log.d("UserImages", "Número de imágenes: ${currentUser.images.size}")

        // Configura el RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // Inicializa el adapter con la lista de imágenes del usuario actual
        adapter = UserImagesAdapter(currentUser.images)
        recyclerView.adapter = adapter

        button.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error al crear el archivo
                    null
                }
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "com.example.android.fileprovider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        ).apply {
            currentPhotoPath = absolutePath
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // Decodifica la imagen capturada y la agrega a la lista de imágenes del usuario actual
            val imageBitmap = BitmapFactory.decodeFile(currentPhotoPath)
            currentUser.addImage(imageBitmap)

            // Realizar el reconocimiento de texto en la imagen


            // Notifica al adaptador que los datos han cambiado
            adapter.notifyDataSetChanged()
        }
    }


}

