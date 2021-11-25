package com.todo;

import java.util.Scanner;

import com.todo.dao.TodoList;
import com.todo.menu.Menu;
import com.todo.service.TodoUtil;

public class TodoMain {
	
	public static void start() {
	
		Scanner sc = new Scanner(System.in);
		TodoList l = new TodoList();
		boolean isList = false;
		boolean quit = false;
		TodoUtil.loadList(l, "todolist.txt");
		Menu.displaymenu();
		do {
			Menu.prompt();
			isList = false;
			String choice = sc.next();
			switch (choice) {

			case "add":
				TodoUtil.createItem(l);
				break;
			
			case "del":
				TodoUtil.deleteItem(l);
				break;
				
			case "edit":
				TodoUtil.updateItem(l);
				break;
				
			case "ls":
				TodoUtil.listAll(l);
				break;
				
			case "find":
				String keyword = sc.next();
				TodoUtil.findList(l, keyword);
				break;
				
			case "ls_cate":
				TodoUtil.listCateAll(l);
				break;
				
			case "find_cate":
				String cate = sc.next();
				TodoUtil.findCateList(l, cate);
				break;

			case "ls_name_asc":
				l.sortByName();
				System.out.println("제목순 정렬 완료 :)");
				isList = true;
				break;

			case "ls_name_desc":
				l.sortByName();
				l.reverseList();
				System.out.println("제목역순 정렬 완료 :)");
				isList = true;
				break;
				
			case "ls_date":
				l.sortByDate();
				System.out.println("날짜순 정렬 완료 :)");
				isList = true;
				break;
				
			case "ls_date_desc":
				l.sortByDate();
				l.reverseList();
				System.out.println("날짜역순 정렬 완료 :)");
				isList = true;
				break;
				
			case "help":
				Menu.displaymenu();
				break;

			case "exit":
				quit = true;
				TodoUtil.saveList(l,"todolist.txt");
				break;

			default:
				System.out.println("위의 명령어 메뉴 중에서 입력해주세요 :( (도움말-help)");
				break;
			}
			
			if(isList) l.listAll(l);
		} while (!quit);
	}
}
