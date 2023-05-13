package org.example;

// Задача2:
// Необходимо написать программу – розыгрыша игрушек в магазине детских
// товаров. В программе должен быть минимум один класс со следующими
// свойствами: id игрушки, текстовое название, количество, частота
// выпадения игрушки (вес в % от 100). Метод добавление новых игрушек
// и возможность изменения веса (частоты выпадения игрушки).
// Возможность организовать розыгрыш игрушек.
// С помощью метода выбора призовой игрушки – мы получаем эту призовую
// игрушку и записываем в список\массив. Это список призовых игрушек,
// которые ожидают выдачи. Еще у нас должен быть метод – получения
// призовой игрушки. После его вызова – мы удаляем из списка\массива
// первую игрушку и сдвигаем массив. А эту игрушку записываем
// в текстовый файл. Не забываем уменьшить количество игрушек

import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class ToyShop {
	static void FileOutputWr(String str) {
		try (FileWriter fw = new FileWriter("prizetoys.txt",
				false)) {
			fw.write(str + "\n");
			fw.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	static void FileOutputApp(String str) {
		try (FileWriter fw = new FileWriter("prizetoys.txt",
				true)) {
			fw.append(str + "\n");
			fw.flush();
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	static int iNumber(String str, int max) {
		boolean flag = false;
		String sName = "";
		int n = 0;
		Scanner iScanner = new Scanner(System.in);
		while (flag == false) {
			System.out.printf(str);
			sName = iScanner.nextLine();
			flag = isNumeric(sName);
			if (flag == true) {
				n = Integer.parseInt(sName);
				if ((n < 0) || (n > max))
					flag = false;
			} else {
				if (sName.equals("")) {
					n = -1;
					flag = true;
				}
			}
		}
		if (n > 1000)
			iScanner.close();
		return n;
	}

	static void ProdSpis(Toys[] pr, Byte rozygrysh) {
		if ((pr.length > 0) && (pr != null)) {
			int allExist = 0;
			int allType = 0;
			for (int i = 1; i <= pr.length; i++) {
				if (pr[i - 1].getQuantity() != 0) {
					allType += 1;
					allExist += pr[i - 1].getQuantity();
				}
			}
			if (rozygrysh == 1) {
				System.out.println("Можно выиграть Игрушки " + allType + " видов из " + allExist +
						", участвующих в Розыгрыше:");
			} else if (rozygrysh == 2) {
				System.out.println("Остались Игрушки " + allType + " видов из " + allExist +
						", участвовавших в Розыгрыше:");
			}
			for (int i = 1; i <= pr.length; i++) {
				if (pr[i - 1].getQuantity() >= 0) {
					if (rozygrysh == 2) {
						if (pr[i - 1].getQuantity() > 0) {
							System.out.println("  " + pr[i - 1].getId() + "  " +
									pr[i - 1].getName() + " - " +
									pr[i - 1].getQuantity() + " шт. - " +
									pr[i - 1].getProbability() + " %");
						}
					} else if (rozygrysh == 1) {
						System.out.print("  " + pr[i - 1].getId() + "  " +
								pr[i - 1].getName() + " - " +
								pr[i - 1].getQuantity() + " шт. - " +
								pr[i - 1].getProbability() + " %");
						if (pr[i - 1].getQuantity() != 0) {
							System.out.println("  - участвуют в Розыгрыше");
						} else {
							System.out.println("  - можно добавить");
						}
					} else if (rozygrysh == 3) {
						System.out.println("  " + pr[i - 1].getId() + "  " +
								pr[i - 1].getName() + " - " +
								pr[i - 1].getQuantity() + " шт. - " +
								pr[i - 1].getProbability() + " %");
					}
				}
			}
		} else {
			System.out.println("Список пустой!");
		}
	}

	static Toys[] ProdFree(Toys[] pr, int rating) {
		Toys[] temp = new Toys[0];
		if (pr.length > 0) {
			boolean bdel = false;
			for (int i = 0; i < pr.length; i++) {
				if (pr[i].getId() == rating) {
					if (pr[i].getQuantity() > 0) {
						pr[i].setQuantity(pr[i].getQuantity() - 1);
						if (pr[i].getQuantity() == 0) {
							bdel = true;
						}
					} else {
						bdel = true;
					}
				}
			}
			if (bdel == true) {
				temp = new Toys[pr.length - 1];
				int j = 0;
				for (int i = 0; i < pr.length; i++) {
					if (pr[i].getId() == rating) {
					} else {
						temp[j] = pr[i];
						j += 1;
					}
				}
				pr = temp;
			}
		}
		return pr;
	}

	static int[] ProdRating(Toys[] pr) {
		int[] prodrating = new int[0];
		if ((pr.length > 0) && (pr != null)) {
			prodrating = new int[pr.length];
			for (int i = 0; i < pr.length; i++) {
				prodrating[i] = pr[i].getId();
			}
		}
		return prodrating;
	}

	static Toys[] ProdBuy(Toys[] pr, Toys[] all, int rating) {
		Toys[] temp = new Toys[0];
		boolean bsum = false;
		if (pr.length > 0) {
			for (int i = 0; i < pr.length; i++) {
				if (pr[i].getId() == rating) {
					pr[i].setQuantity(pr[i].getQuantity() + 1);
					String s2 = pr[i].getName();
					int a2 = pr[i].getProbability();
					FileOutputApp(rating + "   -  " + s2 + " -  " + 1 + " шт.       -  " + a2 + " %");
					bsum = false;
					break;
				} else {
					bsum = true;
				}
			}
			if (bsum == true) {
				temp = new Toys[pr.length + 1];
				for (int i = 0; i < pr.length; i++) {
					temp[i] = pr[i];
				}
				pr = temp;
				for (int i = 0; i < all.length; i++) {
					if (all[i].getId() == rating) {
						pr[pr.length - 1] = new Toys(
								0, "", 0, 0);
						String s1 = all[i].getName();
						int a1 = all[i].getProbability();
						pr[pr.length - 1].setId(rating);
						pr[pr.length - 1].setName(s1);
						pr[pr.length - 1].setQuantity(1);
						pr[pr.length - 1].setProbability(a1);
						FileOutputApp(rating + "   -  " + s1 + " -  " + 1 + " шт.       -  " + a1 + " %");
					}
				}
			}
		} else {
			pr = new Toys[1];
			pr[0] = new Toys(0, "", 0, 0);
			for (int i = 0; i < all.length; i++) {
				if (all[i].getId() == rating) {
					String s1 = all[i].getName();
					int a1 = all[i].getProbability();
					pr[0].setId(rating);
					pr[0].setName(s1);
					pr[0].setQuantity(1);
					pr[0].setProbability(a1);
					FileOutputApp(rating + "   -  " + s1 + " -  " + 1 + " шт.       -  " + a1 + " %");
				}
			}
		}
		return pr;
	}

	public static void main(String[] args) {
		Toys constructor = new Toys(1, "Конструктор ", 10, 10);
		Toys robot = new Toys(2, "Робот       ", 10, 20);
		Toys doll = new Toys(3, "Кукла       ", 10, 30);
		Toys figure = new Toys(4, "Фигурка     ", 0, 0);
		Toys teddybear = new Toys(5, "Мишка       ", 0, 0);
		Toys teddytiger = new Toys(6, "Тигр        ", 0, 0);
		Toys plushgiraffe = new Toys(7, "Жираф       ", 0, 0);
		Toys babyelephant = new Toys(8, "Слоненок    ", 0, 0);
		Toys[] all = new Toys[] { constructor, robot, doll, figure,
				teddybear, teddytiger, plushgiraffe, babyelephant };
		System.out.println();
		System.out.println(String.format("%50s", "").replace(' ', '-'));
		System.out.println("Список Игрушек в магазине, готовых для Розыгрыша (10 туров):");
		byte rozygrysh = 1;
		ProdSpis(all, rozygrysh);
		Toys[] buy1 = new Toys[0];
		Toys[] buy2 = new Toys[0];
		Toys[] buy3 = new Toys[0];
		Basket bask1 = new Basket(buy1);
		Basket bask2 = new Basket(buy2);
		Basket bask3 = new Basket(buy3);
		User user1 = new User("Андрей", "Петров", bask1);
		User user2 = new User("Сергей", "Иванов", bask2);
		User user3 = new User("Петр", "Новиков", bask3);
		User[] users = new User[] { user1, user2, user3 };
		System.out.println(String.format("%50s", "").replace(' ', '-'));
		for (int i = 1; i < users.length + 1; i++) {
			System.out.println(i + " Покупатель " + users[i - 1].getFirst_name() + " " +
					users[i - 1].getLast_name() + " готов к Розыгрышу " +
					users[i - 1].getBask());
		}
		FileOutputWr("*** Розыгрыш Игрушек для Покупателей магазина! ***\n\n" +
				"id     нименование     количество     вес игрушки");
		int n = 1;
		int n2 = 0;
		int npr = 0;
		int numb = 1;
		int numb2 = 10;
		int numb3 = 0;
		int numbpr = 100;
		while (n > 0) {
			System.out.println();
			System.out.println("Добавить Игрушку: количество -> 1-10 шт., убрать: кол. -> 0 шт.");
			System.out.println("Сменить вес (выпадение) игрушки: 1-100, убрать вес: -> 0");
			n = iNumber("Добавить/убрать Игрушку: да = 1 / не менять = 0 или Enter: ", numb);
			if ((n == 0) || (n == -1)) {
				break;
			}
			n2 = iNumber("Количество игрушек Конструктор: 0-10 / не менять = Enter: ", numb2);
			numb3 = n2 == -1 ? all[0].getQuantity() : n2;
			npr = iNumber("Сменить вес (выпадение) игрушки: 0-100 / нет = Enter(= 10): ", numbpr);
			npr = npr == -1 ? 10 : npr;
			constructor = new Toys(1, "Конструктор ", numb3, npr);

			n2 = iNumber("Количество игрушек Робот:       0-10 / не менять = Enter: ", numb2);
			numb3 = n2 == -1 ? all[1].getQuantity() : n2;
			npr = iNumber("Сменить вес (выпадение) игрушки: 0-100 / нет = Enter(= 20): ", numbpr);
			npr = npr == -1 ? 20 : npr;
			robot = new Toys(2, "Робот       ", numb3, npr);

			n2 = iNumber("Количество игрушек Кукла:       0-10 / не менять = Enter: ", numb2);
			numb3 = n2 == -1 ? all[2].getQuantity() : n2;
			npr = iNumber("Сменить вес (выпадение) игрушки: 0-100 / нет = Enter(= 30): ", numbpr);
			npr = npr == -1 ? 30 : npr;
			doll = new Toys(3, "Кукла       ", numb3, npr);

			n2 = iNumber("Количество игрушек Фигурка:     0-10 / не менять = Enter: ", numb2);
			numb3 = n2 == -1 ? all[3].getQuantity() : n2;
			npr = iNumber("Сменить вес (выпадение) игрушки: 0-100 / нет = Enter(= 40): ", numbpr);
			npr = npr == -1 ? 40 : npr;
			figure = new Toys(4, "Фигурка     ", numb3, npr);

			n2 = iNumber("Количество игрушек Мишка:       0-10 / не менять = Enter: ", numb2);
			numb3 = n2 == -1 ? all[4].getQuantity() : n2;
			npr = iNumber("Сменить вес (выпадение) игрушки: 0-100 / нет = Enter(= 10): ", numbpr);
			npr = npr == -1 ? 10 : npr;
			teddybear = new Toys(5, "Мишка       ", numb3, npr);

			n2 = iNumber("Количество игрушек Тигр:        0-10 / не менять = Enter: ", numb2);
			numb3 = n2 == -1 ? all[5].getQuantity() : n2;
			npr = iNumber("Сменить вес (выпадение) игрушки: 0-100 / нет = Enter(= 20): ", numbpr);
			npr = npr == -1 ? 20 : npr;
			teddytiger = new Toys(6, "Тигр        ", numb3, npr);

			n2 = iNumber("Количество игрушек Жираф:       0-10 / не менять = Enter: ", numb2);
			numb3 = n2 == -1 ? all[6].getQuantity() : n2;
			npr = iNumber("Сменить вес (выпадение) игрушки: 0-100 / нет = Enter(= 30): ", numbpr);
			npr = npr == -1 ? 30 : npr;
			plushgiraffe = new Toys(7, "Жираф       ", numb3, npr);

			n2 = iNumber("Количество игрушек Слоненок:    0-10 / не менять = Enter: ", numb2);
			numb3 = n2 == -1 ? all[7].getQuantity() : n2;
			npr = iNumber("Сменить вес (выпадение) игрушки: 0-100 / нет = Enter(= 40): ", numbpr);
			npr = npr == -1 ? 40 : npr;
			babyelephant = new Toys(8, "Слоненок    ", numb3, npr);

			all = new Toys[] { constructor, robot, doll, figure,
					teddybear, teddytiger, plushgiraffe, babyelephant };
			rozygrysh = 1;
			System.out.println();
			ProdSpis(all, rozygrysh);
		}
		int allType = 0;
		for (int i = 0; i < all.length; i++) {
			if (all[i].getQuantity() != 0) {
				allType += 1;
			}
		}
		int iPr = 0;
		int sum = 0;
		int iNumb = 0;
		int j = 0;
		int[] ia100 = new int[100];
		int[] massprob = new int[allType];
		int[] massid = new int[allType];
		for (int i = 0; i < all.length; i++) {
			if (all[i].getQuantity() != 0) {
				iPr = all[i].getProbability();
				massprob[j] = iPr;
				massid[j] = all[i].getId();
				j += 1;
				sum += iPr;
			}
		}
		double koef = 100.0 / sum;
		if (koef > 1.0) {
			koef = 1.0;
		}
		int iSumStop = 0;
		int iSumStart = 0;
		int sumMassprob = 0;
		for (int i = 0; i < allType; i++) {
			iSumStart = (int) (sumMassprob * koef) / 1;
			iNumb = (int) (massprob[i] * koef) / 1;
			iSumStop = iSumStart + iNumb;
			sumMassprob += massprob[i];
			for (j = iSumStart; j < iSumStop; j++) {
				if (j == 101) {
					j = 100;
				}
				ia100[j] = massid[i];
			}
			if (ia100[99] == 0) {
				ia100[99] = ia100[98];
			}
		}
		int usernumb = 0;
		System.out.println();
		System.out.println(String.format("%50s", "").replace(' ', '-'));
		System.out.println(" ***** Розыгрыш 10 Игрушек для Покупателей! ***** ");
		System.out.println(String.format("%50s", "").replace(' ', '-'));
		buy1 = new Toys[0];
		buy2 = new Toys[0];
		buy3 = new Toys[0];
		Boolean bexisttoy = true;
		for (int k = 0; k < 10; k++) {
			usernumb = (int) (Math.random() * (3)) + 1;
			int idrand = ia100[(int) (Math.random() * (100))];
			if (idrand > 0) {
				boolean bexist = false;
				int[] massrating = new int[0];
				massrating = ProdRating(all);
				for (int i = 0; i < massrating.length; i++) {
					if (idrand == massrating[i]) {
						bexist = true;
					}
				}
				if (usernumb == 1) {
					buy1 = ProdBuy(buy1, all, idrand);
					all = ProdFree(all, idrand);
					bask1 = new Basket(buy1);
				} else if (usernumb == 2) {
					buy2 = ProdBuy(buy2, all, idrand);
					all = ProdFree(all, idrand);
					bask2 = new Basket(buy2);
				} else if (usernumb == 3) {
					buy3 = ProdBuy(buy3, all, idrand);
					all = ProdFree(all, idrand);
					bask3 = new Basket(buy3);
				}
				if (bexist == false) {
					System.out.println();
					System.out.println("-- Игрушка данного вида выбрана полностью! --");
				}
			} else {
				bexisttoy = false;
				System.out.println(usernumb + " Покупатель - Игрушка не выпала!");
			}
		}
		if (bexisttoy) {
			System.out.println("Выпали все Игрушки!");
		}
		user1 = new User("Андрей", "Петров", bask1);
		user2 = new User("Сергей", "Иванов", bask2);
		user3 = new User("Петр", "Новиков", bask3);
		users = new User[] { user1, user2, user3 };
		System.out.println(String.format("%50s", "").replace(' ', '-'));
		String sStr = "";
		for (int i = 1; i < users.length + 1; i++) {
			sStr = i + " Покупатель " +
					users[i - 1].getFirst_name() + " " +
					users[i - 1].getLast_name() +
					" выиграл: " +
					users[i - 1].getBask();
			System.out.println(sStr);
			FileOutputApp("\n" + sStr);
		}
		if ((all.length == 0) || (all[0] == null)) {
			all = new Toys[1];
			all[0] = new Toys(
					0, "Нет Игрушек!", 0, 0);
		} else {
			System.out.println(String.format("%50s", "").replace(' ', '-'));
			System.out.println("***** Список оставшихся Игрушек в Розыгрыше: ");
			rozygrysh = 2;
			ProdSpis(all, rozygrysh);
		}
		System.out.println(String.format("%50s", "").replace(' ', '-'));
		System.out.println("***** Список неразыгранных Игрушек в магазине после Розыгрыша:");
		rozygrysh = 3;
		ProdSpis(all, rozygrysh);
		System.out.println();
	}
}
