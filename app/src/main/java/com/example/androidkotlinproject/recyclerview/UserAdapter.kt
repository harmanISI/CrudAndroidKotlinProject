package com.example.androidkotlinproject.recyclerview

import android.app.AlertDialog
import android.view.*
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.androidkotlinproject.R
import com.example.androidkotlinproject.db.UserTable
import com.example.androidkotlinproject.entites.User

class UserAdapter(private val dataSet : MutableList<User>) : RecyclerView.Adapter<UserAdapter.UserItemViewHolder>() {

    class UserItemViewHolder(private val containerView : View) : RecyclerView.ViewHolder(containerView){
        private val userTable = UserTable(containerView.context)
        lateinit var parentAdapter : UserAdapter
        var user : User? = null

        val id : TextView = containerView.findViewById(R.id.item_idValue)
        val firstname : TextView = containerView.findViewById(R.id.item_firstNameValue)
        val lastname : TextView = containerView.findViewById(R.id.item_lastNameValue)
        val emailId : TextView = containerView.findViewById(R.id.item_emailIdValue)
        val course : TextView = containerView.findViewById(R.id.View_course)
        init{
            containerView.setOnLongClickListener {
                val popupMenu = PopupMenu(containerView.context,containerView)
                popupMenu.inflate(R.menu.show_menu)
                popupMenu.setOnMenuItemClickListener {
                    when(it.itemId){
                        R.id.menu_update ->{
                            val dialogView = LayoutInflater.from(containerView.context).inflate(R.layout.update_dialog,null)
                            val builder = AlertDialog.Builder(containerView.context)
                                .setView(dialogView)
                                .setTitle("Update Record")

                            val alertDialog = builder.show()

                            val dialog_firstname : EditText = dialogView.findViewById(R.id.dialog_firstName)
                            val dialog_lastname : EditText = dialogView.findViewById(R.id.dialog_lastName)
                            val dialog_emailId : EditText = dialogView.findViewById(R.id.dialog_emailId)
                            val dialog_updateButton : Button = dialogView.findViewById(R.id.dialog_updateButton)

                            dialog_firstname.setText(user?.firstName)
                            dialog_lastname.setText(user?.lastName)
                            dialog_emailId.setText(user?.email)

                            dialog_updateButton.setOnClickListener {
                                alertDialog.dismiss()
                                user?.firstName = dialog_firstname.text.toString()
                                user?.lastName = dialog_lastname.text.toString()
                                user?.email = dialog_emailId.text.toString()

                                userTable.update(user)
                                parentAdapter.updateData(user as User)
                                Toast.makeText(containerView.context, "Data Updated of id : ${user!!.id}", Toast.LENGTH_SHORT).show()
                            }
                            true
                        }
                        R.id.menu_delete ->{
                            userTable.delete(user)
                            parentAdapter.removeData(user as User)
                            Toast.makeText(containerView.context, "Data Deleted of id : ${user!!.id}", Toast.LENGTH_SHORT).show()
                            true
                        }
                        else -> true
                    }
                }
                popupMenu.show()
                    true
            }
        }
    }

    fun removeData(user : User){
        dataSet.remove(user)
        notifyDataSetChanged()
    }

    fun updateData(user : User){
        for (userIndex in dataSet) {
            if (userIndex.id == user.id){
                userIndex.firstName = user.firstName
                userIndex.lastName = user.lastName
                userIndex.email = user.email
                break
            }
        }
        notifyDataSetChanged()
    }

    //inflate the customView
    override fun onCreateViewHolder(parent: ViewGroup, customViewType: Int): UserItemViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.data_item,parent,false)
        return UserItemViewHolder(view)
    }

    //Called by the layoutManager to replace the content(data) of the CustomView
    override fun onBindViewHolder(holder: UserItemViewHolder, positionInDataSet: Int) {
        val currentData = dataSet[positionInDataSet]
        holder.parentAdapter = this
        holder.user = currentData
        holder.id.text = currentData.id.toString()
        holder.firstname.text = currentData.firstName
        holder.lastname.text = currentData.lastName
        holder.emailId.text = currentData.email
        holder.course.text = currentData.course
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}