package uz.turgunboyevjurabek.firebaseauthandrealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.databinding.ActivityMainBinding
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.madels.User

private const val TAG="MainActivity"
class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private lateinit var auth: FirebaseAuth
    private lateinit var resendingToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var reference: DatabaseReference
    lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var user: User
    lateinit var googleSignInClient: GoogleSignInClient
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
        googleSignInClient= GoogleSignIn.getClient(this,gso)
        firebaseDatabase= FirebaseDatabase.getInstance()
        reference=firebaseDatabase.getReference("MyUsers")

        auth=FirebaseAuth.getInstance()
        binding.btnSign.setOnClickListener {
            signIn()
        }

    }
    private fun signIn(){
        val signInIntent=googleSignInClient.signInIntent
        startActivityForResult(signInIntent,1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1){
            val task=GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account=task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account.idToken)
            }catch (e:ApiException){

                Log.d(TAG,"Google sign in failed")
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String?){
        val credential=GoogleAuthProvider.getCredential(idToken,null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener {
                if (it.isSuccessful){

                    Toast.makeText(this, "Mufaqqiyatli ura", Toast.LENGTH_SHORT).show()
                    val intent=Intent(this,MainActivity2::class.java)
                    user=User(auth.currentUser?.uid,auth.currentUser?.displayName.toString(),auth.currentUser?.photoUrl.toString())
                    reference.child(user.id!!).setValue(user)
                    startActivity(intent)
                }else{
                    Toast.makeText(this, "Mufaqqiyatli emas vay", Toast.LENGTH_SHORT).show()
                }
            }
    }
}