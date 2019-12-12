<?php

namespace App\Services;

use App\Services\Contract\ServiceInterface\AuthenticationServiceInterface;

use App\Http\Requests\User\LoginRequest;
use App\OAuthClient;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

use Symfony\Component\HttpFoundation\Response;
class LoginService implements AuthenticationServiceInterface
{
    
    public function login(LoginRequest $request)
    {   
        
        $user=User::where('login',$request['login'])->first();
        
        if($user){
            if(Hash::check($request['password'], $user['password'])){
                return $this->createTokenByCredentials($user->id,$request->all());
            }else{
                return response()->json(['errors'=>['message' => 'Mot de passe incorrect']],Response::HTTP_UNPROCESSABLE_ENTITY);
            }
        }else{
            return response()->json(['errors'=>['message' => 'Nom d\'utilisateur incorrect']],Response::HTTP_UNPROCESSABLE_ENTITY);
        }
    }

    public function logout()
    {
        try{
            $accessToken=Auth::user()->token();
            $accessToken->revoke();
            DB::table('oauth_access_tokens')->where('id', $accessToken->id)->delete();
            DB::table('oauth_refresh_tokens')->where('access_token_id', $accessToken->id)->delete();
            return response()->json(['success' => [ 'message' => 'Logout successful']],Response::HTTP_NO_CONTENT);
        }
        catch(\Exception $e){
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_NOT_FOUND);
        }
    }

    public function createTokenByCredentials($id,array $request)
    {
        try{
            $oauth_client=OAuthClient::where('user_id',$id)->first();
            if($oauth_client){
                $authenticate = Request::create('/oauth/token', 'POST', [
                    'grant_type' => 'password',
                    'client_id' => $oauth_client->id,
                    'client_secret' => $oauth_client->secret,
                    'username' => $request['login'],
                    'password' => $request['password'],
                ]);
            }else{
                $authenticate = Request::create('/oauth/token', 'POST', [
                    'grant_type' => 'password',
                    'client_id' => config('services.vue_client.id'),
                    'client_secret' =>  config('services.vue_client.secret'),
                    'username' => $request['login'],
                    'password' => $request['password'],
                ]);
            }
                            
            return app()->handle($authenticate);              
        }
        catch(\Exception $e){
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],401);
        }
    }

}