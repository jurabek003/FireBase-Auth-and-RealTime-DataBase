package uz.turgunboyevjurabek.firebaseauthandrealtimedatabase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.adapter.RvAdapter
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.databinding.ActivityMain2Binding
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.madels.User

class MainActivity2 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSingInClient: GoogleSignInClient
    private lateinit var reference: DatabaseReference
    lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var rvAdapter: RvAdapter
    private lateinit var list: ArrayList<User>
    private val binding by lazy { ActivityMain2Binding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.default_web_client_id))
                    .requestEmail()
                    .build()
        auth= FirebaseAuth.getInstance()
        firebaseDatabase= FirebaseDatabase.getInstance()
        reference=firebaseDatabase.getReference("MyUsers")
        list=ArrayList()
        reference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               val children = snapshot.children
                for (child in children){
                   val user=child.getValue(User::class.java)
                    if (user!=null){
                        list.add(user)
                        rvAdapter=RvAdapter(list)
                        binding.rv.adapter=rvAdapter
                    }
                }
                rvAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        googleSingInClient = GoogleSignIn.getClient(this, gso)
        binding.thtBack.setOnClickListener {
            auth.signOut()
            googleSingInClient.signOut()
                .addOnCompleteListener {
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
        }

    }
}