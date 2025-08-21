package lab.cliente;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Cliente {

	public static void main(String[] args) {

		try {
			System.out.println("[Cliente] Inicio");
			
			Scanner teclado = new Scanner(System.in);
			System.out.println("Digite o IP:");
			String ip = teclado.nextLine();
			
			System.out.println("Digite a porta:");
			int porta = Integer.parseInt(teclado.nextLine());
			
			//Cria um objeto socket que se conecta na porta escolhida na maquina de endereço informado
			Socket clientSocket = new Socket(ip, porta);

			//Cria um fluxo de saída (output stream) que permitira
			//enviar dados para a outra ponta do socket (servidor)
			PrintWriter saidaServidor = new PrintWriter(clientSocket.getOutputStream(), true);
			
			//Cria um fluxo de entrada (input stream) que permitira ler
			//o que for eviado pela outra ponta do socket (servidor)
			BufferedReader entradaServidor = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

			System.out.println("[Cliente] Digite a mensagem que será enviada para o Servidor:");
			String mensagemParaServidor = teclado.nextLine();
			//Aqui a mensagem é enviada para a outra ponta do socket(servidor)
			saidaServidor.println(mensagemParaServidor);

			Thread.sleep(100);
			
			//Aqui a resposta enviada pelo servidor pelo socket é lida
			String resposta = entradaServidor.readLine();
			
			System.out.printf("[Cliente] Resposta:%s\n", resposta);
			
			//Fecha e libera os recursos utilizados
			saidaServidor.close();
			entradaServidor.close();
			clientSocket.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("[Cliente] Fim");
		}
	}
}
