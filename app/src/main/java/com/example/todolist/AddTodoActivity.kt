package com.example.todolist

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolist.databinding.ActivityAddTodoBinding
import com.example.todolist.db.AppDatabase
import com.example.todolist.db.TodoDao
import com.example.todolist.db.TodoEntity

class AddTodoActivity : AppCompatActivity() {

    lateinit var binding: ActivityAddTodoBinding
    lateinit var db : AppDatabase
    lateinit var todoDao : TodoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = AppDatabase.getInstance(this)!!
        todoDao = db.getTodoDao()

        binding.checkBtn.setOnClickListener {
            insertTodo()
        }
    }
    // desc 할 일 추가 함수
    private fun insertTodo() {
        val todoTitle = binding.ediTitle.text.toString()    // 할 일 제목
        var todoImportance = binding.radioGroup.checkedRadioButtonId    // 할 일 중요도

        // 어떤 버튼이 눌렸는지 확인하고 값을 지정해줌
        when(todoImportance) {
            R.id.high_btn -> todoImportance = 1
            R.id.middle_btn -> todoImportance = 2
            R.id.low_btn -> todoImportance = 3
            else -> todoImportance = -1
        }

        // 중요도 선택 여부 그리고 제목 작성 체크
        if(todoImportance == -1 || todoTitle.isBlank()) {
            Toast.makeText(this, "모든 항목을 채워주세요", Toast.LENGTH_SHORT).show()
        } else {
            Thread {
                todoDao.insertTodo(TodoEntity(null, todoTitle, todoImportance))
                runOnUiThread {    // 아래 작업은 UI 스레드에서 실행
                    Toast.makeText(this, "할 일이 추가 되었습니다", Toast.LENGTH_SHORT).show()
                    finish()    // AddTodoActivity 종료, 다시 MainActivity로 이동
                }
            }.start()
        }
    }
}