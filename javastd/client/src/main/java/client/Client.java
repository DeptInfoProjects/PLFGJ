package client;

import commun.Identification;
import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;
import org.json.JSONObject;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

public class Client {

    Identification moi = new Identification("Michel B", 42);

    Socket connexion;
    int propositionCourante = 50;

    // Objet de synchro
    final Object attenteDéconnexion = new Object();

    public Client(String urlServeur) {

        try {
            connexion = IO.socket(urlServeur);

            System.out.println("on s'abonne à la connection / déconnection ");;

            connexion.on("connect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" on est connecté ! et on s'identifie ");

                    // on s'identifie
                    JSONObject id = new JSONObject(moi);
                    connexion.emit("identification", id);

                }
            });

            connexion.on("disconnect", new Emitter.Listener() {
                @Override
                public void call(Object... objects) {
                    System.out.println(" !! on est déconnecté !! ");
                    connexion.disconnect();
                    connexion.close();

                    synchronized (attenteDéconnexion) {
                        attenteDéconnexion.notify();
                    }
                }
            });

        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

    }

    private void seConnecter() {
        // on se connecte
        connexion.connect();

        System.out.println("en attente de déconnexion");
        synchronized (attenteDéconnexion) {
            try {
                attenteDéconnexion.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.err.println("erreur dans l'attente");
            }
        }
    }

    public static final void main(String []args) {
        try {
            System.setOut(new PrintStream(System.out, true, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Client client = new Client("http://192.168.43.60:10101");
        client.seConnecter();



        System.out.println("fin du main pour le client");

    }

}
