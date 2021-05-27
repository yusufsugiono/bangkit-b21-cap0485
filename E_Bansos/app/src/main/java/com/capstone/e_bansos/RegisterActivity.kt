package com.capstone.e_bansos

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.Toast
import com.capstone.e_bansos.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.w3c.dom.Text
import java.util.regex.Pattern

class RegisterActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    var databaseReference: DatabaseReference ? = null
    private lateinit var binding : ActivityRegisterBinding


    var database: FirebaseDatabase? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getActionBar()?.hide();

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
            Intent(this@RegisterActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        }


        registerfunction()

    }

    private fun registerfunction(){
        binding.btnSignUp.setOnClickListener {
            val email:String = binding.etEmail.text.toString().trim()
            val password:String = binding.etPassword.text.toString().trim()
            if (TextUtils.isEmpty(email)){
                binding.etEmail.setError("Please Enter Email")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)){
                binding.etPassword.setError("Please Enter Password")
                return@setOnClickListener
            }
            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                binding.etEmail.error = "Email is UnValid"
                binding.etEmail.requestFocus()
                return@setOnClickListener
            }
            if (password.isEmpty() || password.length < 6){
                binding.etPassword.error = "Password must be more than 6 charachter"
                binding.etPassword.requestFocus()
                return@setOnClickListener
            }

            register(email,password)
        }
    }

    private fun register(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener(this){
                if (it.isSuccessful){
                    Intent(this@RegisterActivity, MainActivity::class.java).also {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                    }
                }else{
                    Toast.makeText(this,it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this@RegisterActivity, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}