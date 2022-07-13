package bj.assurance.assurancedeces.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;
import bj.assurance.assurancedeces.activity.Connexion;
import bj.assurance.assurancedeces.utils.token.TokenManager;

import static android.content.Context.MODE_PRIVATE;

public class CatchApiError {


    private int responseCode;



    public CatchApiError(int responseCode) {
        this.responseCode = responseCode;
    }



    private void tokenInvalid(Context context) {

        TokenManager.getInstance(((FragmentActivity)context)
                .getSharedPreferences("prefs", MODE_PRIVATE))
                .deleteToken();


        Intent i = new Intent(context, Connexion.class);
        context.startActivity(i);


        try {

            Toast.makeText(context, "Votre session à expiré", Toast.LENGTH_LONG).show();

        } catch (Exception ignored) {}


    }




    public String catchError(Context context) {

        switch (responseCode) {
            case 400:  // Bad Request La syntaxe de la requête est erronée.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";

            case 401:  // Unauthorized  Une authentification est nécessaire pour accéder à la ressource.

                tokenInvalid(context);

                return "Session expirée";

            case 403:  // Forbidden

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";

            case 404: // Not Found Ressource non trouvée.

                return "";

            case 405: //  Method Not Allowed Méthode de requête non autorisée.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";

            case 406:  //  Not Acceptable La ressource demandée n'est pas disponible dans un format qui respecterait les en-têtes « Accept » de la requête.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";

            case 408:   //   Request Time-out Temps d’attente d’une requête du client, écoulé côté serveur. D'après les spécifications HTTP : « Le client n'a pas produit de requête dans le délai que le serveur était prêt à attendre. Le client PEUT répéter la demande sans modifications à tout moment ultérieur »8.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement.";

            case 422:

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";


            case 424:  //  Method failure Une méthode de la transaction a échoué.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";

            case 429: //  Too Many Requests Le client a émis trop de requêtes dans un délai donné.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";

            case 444:  // No Response Indique que le serveur n'a retourné aucune information vers le client et a fermé la connexion.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";

            case 500:  // Internal Server Error Erreur interne du serveur.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";


            case 498:  // Client Closed Request	Le client a fermé la connexion avant de recevoir la réponse. Cette erreur se produit quand le traitement est trop long côté serveur16.

                return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";


            default: return "Oups nous avons rencontré un problème, Veuillez le reporter à l'équipe de développement";
        }

    }
}
