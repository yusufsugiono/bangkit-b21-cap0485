package com.capstone.e_bansos

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.capstone.e_bansos.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.core.view.View

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding

    private lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getActionBar()?.hide();

        auth = FirebaseAuth.getInstance()

        binding.btnLogin.setOnClickListener {
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

            login(email,password)

        }

        binding.btnRegister.setOnClickListener {
            Intent(this@LoginActivity, RegisterActivity::class.java).also {
                startActivity(it)
            }
        }

        binding.btnForgotPassword.setOnClickListener{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setTitle("Forgot Password")
            val view : android.view.View? = layoutInflater.inflate(R.layout.forgot_password,null)
            val emailreset : EditText = view!!.findViewById(R.id.etResetEmail)
            builder.setView(view)
            builder.setPositiveButton("Reset",DialogInterface.OnClickListener { _, _ ->
                forgotpassword(emailreset)
            })
            builder.setNegativeButton("Close",DialogInterface.OnClickListener { _, _ ->  })
            builder.show()
        }


    }

    private fun forgotpassword(emailreset: EditText) {
        if (emailreset.text.toString().isEmpty()){
            return
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailreset.text.toString()).matches()){
            return
        }
        auth.sendPasswordResetEmail(emailreset.text.toString())
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                    Toast.makeText(this,"Email send , Check your Email for Reset", Toast.LENGTH_SHORT).show()
                }
            }

    }

    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful){
                    Intent(this@LoginActivity, MainActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                }else{
                    Toast.makeText(this,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                }
            }

    }

    override fun onStart() {
        super.onStart()
        if (auth.currentUser != null){
            Intent(this@LoginActivity, MainActivity::class.java).also { intent ->
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }
}