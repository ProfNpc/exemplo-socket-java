package lab.servidor;

//import java.io.*;
//import java.net.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Servidor {

	public static void main(String[] args) {
		try {
			System.out.println("[Servidor] Inicio");
			
			Scanner teclado = new Scanner(System.in);
			System.out.println("Digite a porta:");
			int porta = Integer.parseInt(teclado.nextLine());
			teclado.close();
			
			//Cria um objeto ServerSocket para ficar "ouvindo" a porta escolhida
			//aguardando que outro programa se conecte nessa porta
			try(ServerSocket server = new ServerSocket(porta)) {

				//Recupera o endereço IP da maquina que esta rodando esse programa
				InetAddress enderecoIP = server.getInetAddress();
				
				//Recupera, salva e apresenta a maquina, IP e a porta onde esta a aplicacao
				String serverIdentification = String.format("IP:=%s, Porta:%s\n", InetAddress.getLocalHost(), porta);
				System.out.printf("[Servidor] %s\n", serverIdentification);
				
				while(true) {

					//Fica aguardando uma conexão
					Socket socket = server.accept();
					

					try(
						//Cria um fluxo de saída (output stream) que permitira
						//enviar dados para a outra ponta do socket (cliente)
						PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
						
						//Cria um fluxo de entrada (input stream) que permitira ler
						//o que for eviado pela outra ponta do socket (cliente)
						BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))
					) {

						//Lê o que o cliente enviou
						String mensagemRecebidaDoCliente = in.readLine();
						String enderecoCliente = socket.getInetAddress().getHostAddress();
						
						System.out.printf("[Cliente][%s]:%s", enderecoCliente, mensagemRecebidaDoCliente);
						
						if (mensagemRecebidaDoCliente != null && mensagemRecebidaDoCliente.startsWith("eco")) {
							//corta "eco " do que foi recebido
							String mensagemEco = mensagemRecebidaDoCliente.substring(4).trim();
							String respostaEco = String.format("Você enviou '%s'\n", mensagemEco);
							//Envia "Você enviou '<mensagem recebida>' para o cliente"
							out.println(respostaEco);
						}
						
						//Encerra conexão com o cliente
						socket.close();
						
					}
				}
				
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		} catch(Exception e) {
			
		} finally {
			System.out.println("[Servidor] Fim");
		}
	}
}
