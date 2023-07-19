package uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.databinding.ItemRvBinding
import uz.turgunboyevjurabek.firebaseauthandrealtimedatabase.madels.User
import java.util.ArrayList

class RvAdapter(val list: ArrayList<User>):RecyclerView.Adapter<RvAdapter.Vh>() {
    inner class Vh(val itemRvBinding: ItemRvBinding):ViewHolder(itemRvBinding.root){
        fun onBind(user: User){
            itemRvBinding.itemFullName.text=user.fullName.toString()
            Picasso.get().load(user.image).into(itemRvBinding.itemImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemRvBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int =list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }
}