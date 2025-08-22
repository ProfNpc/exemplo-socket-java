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
			
			//Lê o IP da máquina onde a aplicação servidora roda
			String ip = getParametroDoTeclado(teclado, "Digite o IP", "localhost");
			
			//Lê a porta informada pelo usuario pelo teclado
			int porta = getParametroDoTeclado(teclado, "Digite a porta", 8080);

			
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
			saidaServidor.flush();

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
	
	private static String getParametroDoTeclado(Scanner teclado, String mensagemUsuario, String valorPadrao) {
		System.out.printf("%s[%s]:", mensagemUsuario, valorPadrao);
		String nextLine = teclado.nextLine();
		if (nextLine != null && "".equals(nextLine.trim())) {
			return valorPadrao;
		}
		return nextLine;
	}

	private static int getParametroDoTeclado(Scanner teclado, String mensagemUsuario, int valorPadrao) {
		
		String textoLido = getParametroDoTeclado(teclado, mensagemUsuario, valorPadrao + "");

		if (textoLido != null && "".equals(textoLido.trim())) {
			return valorPadrao;
		}
		return Integer.parseInt(textoLido);
	}
}
