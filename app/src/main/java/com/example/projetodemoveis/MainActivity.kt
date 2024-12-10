package com.example.projetodemoveis

import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.projetodemoveis.adapter.MyAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MyAdapter
    private val posts = mutableListOf<Post>() // Lista de posts

    private val auth = FirebaseAuth.getInstance() // Autenticação Firebase
    private val firestore = FirebaseFirestore.getInstance() // Instância Firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuração do RecyclerView
        recyclerView = findViewById(R.id.recyclerView)
        adapter = MyAdapter(posts) // Lista inicial de posts
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Carregar posts do Firestore
        loadPostsFromFirestore()
    }

    private fun postToFirestore(content: String, imageUri: Uri?) {
        val currentUser = auth.currentUser
        val userId = currentUser?.uid ?: "Anônimo"

        // Recuperar o nome do utilizador no Firestore
        firestore.collection("users").document(userId).get()
            .addOnSuccessListener { document ->
                val userName = document.getString("username") ?: "Utilizador Desconhecido"
                val formattedDate =
                    SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

                // Dados do post
                val postMap = hashMapOf(
                    "content" to content,
                    "imageUri" to (imageUri?.toString() ?: ""),
                    "userId" to userId,
                    "userName" to userName,
                    "timestamp" to formattedDate
                )

                // Adicionar post ao Firestore
                firestore.collection("posts")
                    .add(postMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Post publicado com sucesso!", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener { exception ->
                        Toast.makeText(this, "Erro ao publicar o post: ${exception.message}", Toast.LENGTH_SHORT).show()
                    }
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao recuperar utilizador: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun loadPostsFromFirestore() {
        // Ordenar os posts mais recentes
        firestore.collection("posts")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                posts.clear()
                for (document in result) {
                    // Obter os dados do documento
                    val content = document.getString("content") ?: ""
                    val imageUri = document.getString("imageUri")
                    val userName = document.getString("userName") ?: "Utilizador Desconhecido"
                    val timestamp = document.getString("timestamp") ?: "Data desconhecida"

                    // Adicionar post à lista
                    posts.add(Post(content, imageUri, userName, timestamp))
                }

                // Atualizar RecyclerView
                adapter.notifyDataSetChanged()
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Erro ao carregar posts: ${exception.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
