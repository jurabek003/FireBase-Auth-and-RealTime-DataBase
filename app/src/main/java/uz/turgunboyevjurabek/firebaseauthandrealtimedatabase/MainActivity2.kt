package uz.turgunboyevjurabek.firebaseauthandrealtimedatabase

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.adapter.RvAdapter
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.databinding.ActivityMain2Binding
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.madels.User

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSingInClient: GoogleSignInClient
    private lateinit var reference: DatabaseReference
    private lateinit var rvAdapter: RvAdapter
    private lateinit var list: ArrayList<User>
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
        val user=intent.getSerializableExtra("key") as User
        firebaseDatabase= FirebaseDatabase.getInstance()
        reference=firebaseDatabase.getReference("MyUsers")
        list=ArrayList()
        list.add(user)
        reference.setValue(user.fullName)
        rvAdapter=RvAdapter(list)
        binding.rv.adapter=rvAdapter
    }
}