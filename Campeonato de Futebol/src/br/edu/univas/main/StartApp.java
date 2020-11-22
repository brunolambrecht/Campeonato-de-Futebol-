
/* Progrma feito pelos alunos 
 * Bruno Lambrecht e Ronald Pereira 
 */

package br.edu.univas.main;

import java.util.Scanner;

public class StartApp {
	
	public static Scanner sc = new Scanner(System.in);
	public static int size = 50;
	
	
	public static void main(String[] args) {
		
		Match game [] = new Match [size];
		Team team [] = new Team [size];
		Points points [] = new Points [size];
		Team table [] = new Team [size];
		
		do {
			
			System.out.println("\n                  Menu");
			options();
			
			int option = sc.nextInt();
			sc.nextLine();
			
			selectOption(option, team, game, points, table);
			
			if (option == 9) {
				break;
			
			} else if (option != 1 && option != 2 && option != 3 && option != 4 && option != 5 && option != 6 && option != 7) {
				System.out.println("Opção inválida!!");
				
			}
			
		}while (true);
		
		sc.close();
		
	}
		//Selecionar a opção
	
	public static void selectOption(int option, Team team [], Match match [], Points points[], Team table []) {
		
		Team escalation = new Team();		
		Match game = new Match();
		Points point = new Points();
		
		//Cria equipe
		
		if (option == 1) {
			
			trainingTeam(escalation);
			addTeam(team,escalation);	
		}
		
		//Edita time
		
		else if (option == 2) {
			
			System.out.println("Informe a equipe que deseja editar?\n");
			searchTeam(team);			
			int index = place();			
			editTeam(index, team, escalation);
			
		}
		
		//Exclui time
		
		else if (option == 3) {
			
			System.out.println("Informe a equipe que deseja excluir?\n");
			searchTeam(team);	
			int index = place();				
			deleteTeam(team, index , points , match,  game, point);
			for (int i = 0; i < size - 1; i++) {
				
				if (team[i] == null) {
					
					Team aux = team[i];
					team[i] = team[i+1];
					team [i+1] = aux;
				}
			}
		}
		
		//Cria jogo
		
		else if (option == 4) {
			
			int indexMast = registerMastermind(team,game);
			int indexVist = registerVisitor(team,game);
			int index = addGameArray(match,game);
			logicScore(team,game,points,indexMast,indexVist,index, point);
						
		}
		
		//Edita jogo
		
		else if (option == 5) {
			
			System.out.println("Como deseja editar a partida?");
			System.out.println("\n1 - Editar resultado: ");
			System.out.println("2 - Editar times e resultado: ");
			int idx = sc.nextInt();
			sc.nextLine();
			logicEditGameScore(idx, match,game,points,team, point);			
		}
		
		//Exclui jogo
		
		else if (option == 6) {
			
			System.out.println("Qual jogo deseja excluir?\n");
			searchGame(match);
			int index = sc.nextInt();
			deleteGame(match, index, points, game, point);	
		}
		
		//Mostra classificação
	
		else if (option == 7) {
			
			Team classific = new Team ();
			for(int i = 0; i< size;i++) {
				table[i] =  null;
			}
			
			for (int i = 0; i < size; i++) {
				classific = team[i];
				table[i] = classific;
				bubbleSort(table);
			}
			classification(table);
		}
		
	}
	
	public static void trainingTeam (Team formation) {
		
			
		System.out.println("Digite o nome da equipe à ser cadastrada: ");
		formation.name = sc.nextLine();
		System.out.println("Digite o estado de origem da equipe: ");
		formation.state = sc.nextLine();
		
		
	}

	public static void options () {
		
		System.out.println("\n==========================================");
		System.out.println("Selecione uma ação à seguir!");
		System.out.println("1 - Cadastrar time");
		System.out.println("2 - Editar time");
		System.out.println("3 - Excluir Time");
		System.out.println("4 – Cadastrar Jogo");
		System.out.println("5 – Editar Jogo");
		System.out.println("6 – Excluir Jogo");
		System.out.println("7 – Listar Classificação do Campeonato");
		System.out.println("9 - Sair");
		System.out.println("==========================================");
	}
	
	public static void searchTeam(Team formation []) {
		
		for (int i = 0; i < size; i++) {
			
			if (formation[i] != null) {
				
				System.out.println( i + " " + formation[i].name);
				
			}
			
		}
		
	}

	public static void editTeam (int index, Team formation [], Team team) {
		
		for (int i = 0; i < size; i++) {
			
			if (i == index) {
				
				trainingTeam (team);
				formation[i] = team;
				break;
			}
			
		}
		
	}

	public static int place () {
		
		System.out.println("\nDigite o indice do time desejado: ");
		int indice = sc.nextInt();
		sc.nextLine();
		
		return indice;
	}
	
	public static void addTeam (Team formation [], Team team ) {
		
		for (int i = 0; i < size; i++) {
			
			if (formation[i] == null) {
				
				formation[i] = team;
				break;
			}
			
		}
		
	}
	
	public static void deleteTeam (Team formation [],int index, Points pointGol [], Match match [], Match game, Points point) {
		
					
		for (int i = 0; i < size; i++) {
			
			
			if (match[i].mastermind.name.equals(formation[index].name) || match[i].visitor.name.equals(formation[index].name) ) {
				
				game = match[i];
				point = pointGol[i];
				formation [index] = null;
				
				
				for(int j = 0; j < size; j++) {
					
					deleteGame(match, i, pointGol, game, point);
					
				}break;
			}
			
		}
		
	}
	
	public static int registerMastermind (Team formation [], Match game) {
		
		System.out.println("Selecione as equipes que voce quer cadastrar a partida:\n ");
		searchTeam(formation);	
		System.out.println("\nTIME MANDANTE\n ");
		int indice = place();
		game.mastermind = new Team();
		game.mastermind = formation[indice];
		
		System.out.println("Digite agora os gols do time mandante: ");
		game.mastermindGol = sc.nextInt();
		sc.nextLine();
		
		return indice;
		
	}
	
	public static int registerVisitor (Team formation[], Match game) {
		
		searchTeam(formation);
		System.out.println("\nTIME VISITANTE\n ");
		int index = place();
		game.visitor = new Team();
		game.visitor = formation[index];
		
		System.out.println("Digite agora os gols do time visitante: ");
		game.visitorGol = sc.nextInt();
		sc.nextLine();
		
		return index;
	}

	public static void logicScore(Team formation[],Match game, Points pointGol[] , int indexMast, int indexVisit, int index, Points point) {
		
		
		
		if(game.mastermindGol > game.visitorGol) {
			
			point.mastermindPoints = 3;
			point.visitorPoints = 0;
			point.amountGolsMast = game.mastermindGol - game.visitorGol;
			point.amountGolsVisit = game.visitorGol - game.mastermindGol;
			
			formation[indexMast].points = formation[indexMast].points + point.mastermindPoints;
			
			
			formation[indexMast].amountGols = formation[indexMast].amountGols + (point.amountGolsMast);
			formation[indexVisit].amountGols = formation[indexVisit].amountGols + (point.amountGolsVisit);
			
		
		}else if (game.visitorGol > game.mastermindGol) {
			
			point.mastermindPoints = 0;
			point.visitorPoints = 3;
			point.amountGolsMast = game.mastermindGol - game.visitorGol;
			point.amountGolsVisit = game.visitorGol - game.mastermindGol;
			
			
			formation[indexVisit].points = formation[indexVisit].points + 3 ;
			formation[indexVisit].amountGols = formation[indexVisit].amountGols + (point.amountGolsVisit);
			formation[indexMast].amountGols = formation[indexMast].amountGols + (point.amountGolsMast);
			
		}else {
			
			point.mastermindPoints = 1;
			point.visitorPoints = 1;
			formation[indexMast].points++;
			formation[indexVisit].points++;
				
		}
		
		pointGol[index] = point;
	}
	
	public static int addGameArray (Match match [], Match game) {
		
		int i;
		for (i = 0; i < size; i++) {
			
			if (match[i] == null) {
				match[i] = game;
				break;
			}
		}
		
		return i;
	}
	
	public static void searchGame (Match match []) {
		
		for (int i = 0; i < size; i++) {
			
			if (match[i] != null) {
				
				System.out.println( i + " - " + match[i].mastermind.name + " " + match[i].mastermindGol + " X " + match[i].visitorGol + " " + match[i].visitor.name);
										
			}
			
		}
		
	}
	
	public static void editArrayGame (int idx, Match match[], Match game, Points pointGol[], Team formation [], Points point) {
		
		for (int i = 0; i < size; i++) {
			
			if (i == idx) {
				
				int indiceMand = registerMastermind(formation,game);
				int indiceVisit = registerVisitor(formation,game);
				match[idx].mastermind.points = match[idx].mastermind.points - pointGol[idx].mastermindPoints;
				match[idx].visitor.points = match[idx].visitor.points - pointGol[idx].visitorPoints;
				match[idx].mastermind.amountGols = match[idx].mastermind.amountGols - pointGol[idx].amountGolsMast;
				match[idx].visitor.amountGols = match[idx].visitor.amountGols - pointGol[idx].amountGolsVisit;
								
				logicScore(formation,game, pointGol, indiceMand,indiceVisit, idx, point);
				match[i] = game;
				break;
			
			}
			
		}

	}
	
	public static void logicEditGameScore (int idx, Match match[], Match game,Points pointGol [], Team team [], Points point) {
		
		if (idx == 2) {
			
			System.out.println("Qual jogo deseja editar?\n");
			searchGame(match);
			idx = place();
			editArrayGame(idx, match, game, pointGol, team, point);
		
		}else if (idx == 1) {
		
			System.out.println("Qual jogo deseja editar?\n");
			searchGame(match);
			idx = place();
			
			
			System.out.println("Digite os gols do " + match[idx].mastermind.name);
			int mastGol = sc.nextInt();
			sc.nextLine();
			if (mastGol > match[idx].mastermindGol) {
				int result = mastGol - match[idx].mastermindGol;
				pointGol[idx].amountGolsMast = pointGol[idx].amountGolsMast + result;
				match[idx].mastermind.amountGols = match[idx].mastermind.amountGols + result; 
			}else if (mastGol < match[idx].mastermindGol) {
				int result = match[idx].mastermindGol - mastGol;
				pointGol[idx].amountGolsMast = pointGol[idx].amountGolsMast - result;
				match[idx].mastermind.amountGols = match[idx].mastermind.amountGols - result;
			}
			int mastermindGol = match[idx].mastermindGol;
			match[idx].mastermindGol = mastGol;
			
			
			
			System.out.println("Digite os gols do " + match[idx].visitor.name);
			int VisitGol = sc.nextInt();
			sc.nextLine();
			if (VisitGol > match[idx].visitorGol) {
				int result = VisitGol - match[idx].visitorGol;
				pointGol[idx].amountGolsVisit = pointGol[idx].amountGolsVisit + result;
				match[idx].visitor.amountGols = match[idx].visitor.amountGols + result;
				
			}else if (VisitGol < match[idx].visitorGol) {
				int result = match[idx].visitorGol - VisitGol;
				pointGol[idx].amountGolsVisit = pointGol[idx].amountGolsVisit - result;
				match[idx].visitor.amountGols = match[idx].visitor.amountGols - result;
			}
			int visitorGol = match[idx].visitorGol;
			match[idx].visitorGol = VisitGol;
			
			
			if (mastGol > VisitGol) {
				
				if (mastermindGol < visitorGol) {
					
					pointGol[idx].mastermindPoints = 3;
					match[idx].mastermind.points = match[idx].mastermind.points + 3; 
					pointGol[idx].visitorPoints = 0;
					match[idx].visitor.points = match[idx].visitor.points - 3;
					
				}else if (mastermindGol ==  visitorGol) {
					
					pointGol[idx].mastermindPoints = 3;
					match[idx].mastermind.points = match[idx].mastermind.points + 2; 
					pointGol[idx].visitorPoints = 0;
					match[idx].visitor.points = match[idx].visitor.points - 1;
										
				}
				
			}else if (VisitGol > mastGol) {
				
				if (visitorGol < mastermindGol) {
					
					pointGol[idx].mastermindPoints = 0;
					match[idx].mastermind.points = match[idx].mastermind.points -3; 
					pointGol[idx].visitorPoints = 3;
					match[idx].visitor.points = match[idx].visitor.points + 3;
					
				}else if (mastermindGol ==  visitorGol) {
					
					pointGol[idx].mastermindPoints = 0;
					match[idx].mastermind.points = match[idx].mastermind.points -1; 
					pointGol[idx].visitorPoints = 3;
					match[idx].visitor.points = match[idx].visitor.points + 2;
					}
				
			}else {
				
				if (mastermindGol > visitorGol) {
					
					pointGol[idx].mastermindPoints = 1;
					match[idx].mastermind.points = match[idx].mastermind.points - 2; 
					pointGol[idx].visitorPoints = 1;
					match[idx].visitor.points = match[idx].visitor.points + 1;
					
				}else if (mastermindGol < visitorGol) {
					
					pointGol[idx].mastermindPoints = 1;
					match[idx].mastermind.points = match[idx].mastermind.points + 1; 
					pointGol[idx].visitorPoints = 1;
					match[idx].visitor.points = match[idx].visitor.points - 2;	
				}
					
			}
			
		}
		
	}
	
	public static void deleteGame(Match match [], int index, Points pointGol [],Match game, Points point) {
		
				
		if (game.mastermind == null && match[index] != null) {
			
			game.visitor.amountGols = game.visitor.amountGols - point.amountGolsVisit;
			game.visitor.points = game.visitor.points - point.visitorPoints;
			
		}else if (game.visitor == null && match[index] != null){
			
			game.mastermind.points = game.mastermind.points - point.mastermindPoints;
			game.mastermind.amountGols = game.mastermind.amountGols - point.amountGolsMast;
			
		}else if (match[index] != null) {
			
			game.mastermind.points = game.mastermind.points - point.mastermindPoints;
			game.visitor.points = game.visitor.points - point.visitorPoints;
			game.mastermind.amountGols = game.mastermind.amountGols - point.amountGolsMast;
			game.visitor.amountGols = game.visitor.amountGols - point.amountGolsVisit;
		
		}
		pointGol[index] = point;
		pointGol[index] = null;
		match[index] = game;
		match[index] = null;
		
	}
	
	public static void bubbleSort (Team table []) {
		
		boolean swap = true;
		
		do {
			swap = false;
			for (int j = 0; j < size - 1; j++) {
				if ((table[j+1] != null) && (table[j] != null) && (table[j].points < table[j + 1].points)) {
					Team aux = new Team ();
					aux = table[j];
					table[j] = table[j + 1];
					table[j+1] = aux;
					swap = true;
				}
			}
			
			for(int i = 0; i < size -1; i++) {
				swap = false;
				
				if((table[i+1] != null) && (table[i] != null) && (table[i].points == table[i+1].points)) {
					
					
					if (table[i].amountGols < table[i+1].amountGols) {
						System.out.println("entrei no 2 if");
						
						Team aux = new Team ();
						aux = table [i];
						table[i] = table[i+1];
						table[i+1] = aux;
						swap = true;
					}
					
				}
					
			}
			
		} while(swap);
			
	}
		// Classificação do Campeonato
	public static void classification (Team table []) {
		
		System.out.println("\nClassificação do Campeonato:\n");
		for (int i = 0; i < size; i++) {
			
			if (table[i] != null) {
				
				System.out.println((i+1)+ "° " + table[i].name + " do estado " + table[i].state + " com " + table[i].points + " pontos e " + table[i].amountGols + " de saldo de gols");
				
			}
			
		}
		
	}
}