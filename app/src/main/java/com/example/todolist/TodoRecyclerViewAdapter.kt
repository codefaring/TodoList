package com.example.todolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.databinding.ItemTodoBinding
import com.example.todolist.db.TodoEntity

class TodoRecyclerViewAdapter(private val todoList : ArrayList<TodoEntity>,
    private val listener: OnItemLongClickListener) : RecyclerView.Adapter<TodoRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {
        val txt_importance = binding.txtImportance
        val txt_title = binding.todoTitle

        // 뷰 바인딩에서 기본적으로 제공하는 루트 변수는 레이아웃의 루트를 의미함.
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // item_todo.xml 뷰 바인딩 객체 생성
        val binding : ItemTodoBinding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context),
            parent, false)
        return MyViewHolder(binding)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todoData = todoList[position]
        // 중요도에 따라 색상을 변경
        when(todoData.importance) {
            1 -> holder.txt_importance.setBackgroundResource(R.color.coral)
            2 -> holder.txt_importance.setBackgroundResource(R.color.sky)
            3 -> holder.txt_importance.setBackgroundResource(R.color.grey)
        }

        // 중요도에 따라 중요도 텍스트(1, 2, 3) 변경
        holder.txt_importance.text = todoData.importance.toString()

        // 할 일의 제목 변경
        holder.txt_title.text = todoData.title

        // 할 일을 길게 누르면 리스너 실행
        holder.root.setOnLongClickListener {
            listener.onLongClick(position)
            false
        }
    }

    override fun getItemCount(): Int {
        // 리사이클러뷰 아이템 개수는 할 일 리스트 크기
        return todoList.size
    }

}