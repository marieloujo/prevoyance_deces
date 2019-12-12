<?php

use App\Models\Departement;
use Illuminate\Http\Request;

//use Illuminate\Support\Facades\Route;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::group(['middleware' => 'auth:api'], function () {
    Route::get('user','Api\UserController@getAuthenticatedUserable');
    
    Route::post('logout','Api\Auth\LoginController@logout');

    Route::group(['prefix'=>'marchands'],function(){
        
        Route::get('/{marchand}/clients','Api\MarchandController@getClients');
        Route::get('/{marchand}/prospects','Api\MarchandController@getProspects');
        Route::get('/{marchand}/clients/{client}/contrats','Api\MarchandController@getContrats');
        Route::get('/{marchand}/transactions','Api\MarchandController@getTransaction');
        Route::get('/{marchand}/transactions/{date?}','Api\MarchandController@getTransaction');
        Route::get('/{marchand}/getAllTransactions','Api\MarchandController@getTransactions');
        Route::get('/{marchand}/getCompte','Api\MarchandController@getCompte');
        Route::get('/{marchand}/getComptes','Api\MarchandController@getComptes');
        Route::get('/{marchand}/getComptes/{date?}','Api\MarchandController@getComptes');
        
    });


    Route::group(['prefix'=>'supermarchands'],function(){
        
        Route::get('/{supermarchand}/marchands', 'Api\SuperMarchandController@getMarchands' );
        Route::get('/{supermarchand}/getCompte','Api\SuperMarchandController@getCompte');
        Route::get('/{supermarchand}/getComptes','Api\SuperMarchandController@getComptes');
        Route::get('/{supermarchand}/getComptes/{date?}','Api\SuperMarchandController@getComptes');
        
    });

    Route::group(['prefix'=>'departements'],function(){
        Route::get('/{departement}/communes/{commune}/marchands','Api\CommuneController@getMarchands');
        Route::get('/{departement}/communes','Api\DepartementController@getCommunes');
    });


    Route::group(['prefix'=>'communes'],function(){
        Route::get('/{commune}/users','Api\CommuneController@getUsers');
        Route::get('/{commune}/departement','Api\CommuneController@getDepartement');
    });



    Route::group(['prefix'=>'clients'],function(){
        Route::get('/{client}/lastContrats','Api\ClientController@getLastContrats');
        Route::get('/{client}/contrats','Api\ClientController@getContrats');
    });

    Route::group(['prefix'=>'users'],function(){
         Route::get('/{userPhone}/telephone','Api\UserController@readByPhone');
         Route::get('/{userNom}/nom','Api\UserController@readByNom');
         Route::get('/{user}/conversations','Api\UserController@getDiscussions');
         Route::get('/{user}/notifications','Api\UserController@getNotifications');
    });

    

    Route::group(['prefix'=>'contrats'],function(){
        Route::get('/{refContrat}/contrat','Api\ContratController@showContrat');
   });

    Route::apiResource('marchands','Api\MarchandController');

    Route::apiResource('users','Api\UserController');

    Route::apiResource('clients','Api\ClientController');

    Route::apiResource('documents','Api\DocumentController');

    Route::apiResource('roles','Api\RoleController');

    Route::apiResource('contrats','Api\ContratController');

    Route::apiResource('conversations','Api\ConversationController');

    Route::apiResource('conversations_users','Api\ConversationUserController');

    Route::apiResource('communes','Api\CommuneController');

    Route::apiResource('directions','Api\DirectionController');

    Route::apiResource('departements','Api\DepartementController');

    Route::apiResource('comptes','Api\CompteController');

    Route::apiResource('messages','Api\MessageController');

    Route::apiResource('beneficiaires','Api\BeneficiaireController');

    Route::apiResource('benefices','Api\BeneficeController');

    Route::apiResource('supermarchands','Api\SuperMarchandController');

    Route::apiResource('assures','Api\AssurerController');

    Route::apiResource('portefeuilles','Api\PortefeuilleController');
    
});

Route::post('login','Api\Auth\LoginController@login');
Route::post('register','Api\Auth\RegisterController@register');
