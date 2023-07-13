package uz.turgunboyevjurabek.firebaseauthandrealtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSingInClient: GoogleSignInClient
    private lateinit var reference: DatabaseReference
    lateinit var firebaseDatabase: FirebaseDatabase
    private val binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
        auth= FirebaseAuth.getInstance()
        val user=auth.currentUser
        firebaseDatabase= FirebaseDatabase.getInstance()
        reference=firebaseDatabase.getReference("MyUsers")
        reference.setValue(user?.email)

    }
}