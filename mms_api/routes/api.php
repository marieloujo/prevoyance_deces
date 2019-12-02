<?php

use App\Models\Contrat;
use App\Models\Portefeuille;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

use Symfony\Component\HttpFoundation\Response;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Carbon\Carbon;
use Illuminate\Support\Facades\DB;
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

// Route::middleware('auth:api')->post('/logout', function (Request $request) {
//     $request->user()->token()->revoke();
//     return response()->json([
//         'message' => 'Successfully logged out'
//     ]);
// });

Route::group(['middleware' => 'auth:api'], function(){
    
    Route::get('user','Api\UserController@user');
    
    Route::post('logout','Api\UserController@logout');
    
    Route::apiResource('contrats','Api\ContratController');
    Route::apiResource('clients','Api\ClientController');
    Route::get('/clients/{client}/contrats/','Api\ClientController@getContrats');
    Route::get('/marchands/{marchand}/contrats/','Api\MarchandController@getContrats');
    Route::get('/marchands/{marchand}/transactions/','Api\MarchandController@getTransaction');

    Route::get('/departements/{departement}/communes/','Api\DepartementController@getCommunes');

    Route::group(['prefix'=>'users'],function(){
        Route::get('/marchands/commune','Api\MarchandController@showByCommune');
        Route::get('/messages','Api\MessageController@conversations');
        Route::get('/notifications','Api\MessageController@notifications');
        Route::get('/searchByName/{name}','Api\UserController@showByName');
        Route::get('/searchByPhone/{phone}','Api\UserController@showByPhone');
        Route::get('/getCompte','Api\CompteController@getCompte');
        Route::get('/getCompteTime/{end}','Api\CompteController@getCompteTime');
        Route::get('/comptes','Api\CompteController@all');
        Route::get('/statistique','Api\UserController@statistique');
        Route::get('usereable','Api\UserController@getUser');
    });

});

Route::post('login','Api\UserController@login');
Route::post('register','Api\UserController@register');

Route::get('get', function(){
    return 'errororrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr';
});

//save client

// souscrire pour client //get client

//depot dans portefeuille

//demande de payement

//

//enregistrer prospect

//api resource departement