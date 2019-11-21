<?php

use Illuminate\Http\Request;

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

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});


Route::post('login','Api\UserController@login');
Route::post('logout','Api\UserController@logout');
Route::post('register','Api\UserController@register');

//Route::group(['middleware' => 'auth:api'], function() {
    
    Route::get('user','Api\UserController@user');
    Route::post('logout','Api\UserController@logout');
                
    Route::get('clients/{name}','Api\AndroidController@client');
    Route::get('marchands/{name}','Api\AndroidController@marchand');
    Route::get('clients/','Api\AndroidController@marchandzone');
    Route::get('super_marchands/{name}','Api\AndroidController@super_marchand');
//});
