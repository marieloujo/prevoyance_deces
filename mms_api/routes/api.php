<?php

use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

use Symfony\Component\HttpFoundation\Response;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
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
    Route::get('marchands/commune','Api\MarchandController@showByCommune');
    
    Route::post('logout','Api\UserController@logout');
    
    Route::apiResource('clients','Api\ClientController');
    Route::apiResource('users','Api\UserController');

    Route::get('search-user-name/{name}','Api\UserController@showByName');
    Route::get('search-user-phone/{phone}','Api\UserController@showByPhone');
    Route::get('statistique','Api\UserController@statistique');
    Route::get('marchands/{departement}','Api\MarchandController@showByCommune');
});

Route::post('login','Api\UserController@login');
Route::post('register','Api\UserController@register');


//save client

// souscrire pour client //get client

//depot dans portefeuille

//demande de payement

//

//enregistrer prospect

//