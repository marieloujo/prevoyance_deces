<?php

namespace App\Repositories\User;

use App\User;
use App\OAuthClient;
use Illuminate\Http\Request;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\DB;
use Illuminate\Support\Facades\Hash;

class UserRepository implements UserRepositoryInterface
{
    protected $user;

    public function __construct(User $user)
    {
        $this->user = $user;
    }
    /**
     * Get a user by it's ID
     *
     * @param int
     * @return collection
     */

    public function getById($id)
    {
        return $this->user->findOrfail($id);
    }

    public function getAuth()
    {
        return Auth::user();
    }

    public function getByPhone($phone)
    {
        return $this->user->where('telephone','=',$phone)->first();
    }

    /**
     * Get a user by it's ID
     *
     * @param string
     * @return collection
     */

    public function getByName($name)
    {
        return $this->user->where('nom',$name)->orWhere('prenom',$name)->get();
    }

    /**
     * Get's all users.
     *
     * @return mixed
     */
    public function all()
    {
        return $this->user->paginate();
    }

    /**
     * Login user.
     *
     * @return mixed
     */
    public function login(array $user_data,$client_id,$client_secret)
    {
        //return response()->json(['error'=> 'OK'/* ['password' => 'Password incorrect'] */],422);
        $credentials = [
            'login' => $user_data['login'],
            'password' => $user_data['password']
        ];

        $user=User::where('login',$user_data['login'])->first();
        
        if($user){
            if(Hash::check($user_data['password'], $user['password'])){

                $oauth_client=OAuthClient::where('user_id',$user->id)->first();
                if($oauth_client){
                    $authenticate = Request::create('/oauth/token', 'POST', [
                        'grant_type' => 'password',
                        'client_id' => $oauth_client->id,//env('VUE_CLIENT_ID'),//config('services.vue_client.id'),
                        'client_secret' => $oauth_client->secret,// env('VUE_CLIENT_SECRET'),// config('services.vue_client.secret'),
                        'username' => $user_data['login'],
                        'password' => $user_data['password'],
                    ]);
                }else{
                    $authenticate = Request::create('/oauth/token', 'POST', [
                        'grant_type' => 'password',
                        'client_id' => $client_id,//env('VUE_CLIENT_ID'),//config('services.vue_client.id'),
                        'client_secret' => $client_secret,// env('VUE_CLIENT_SECRET'),// config('services.vue_client.secret'),
                        'username' => $user_data['login'],
                        'password' => $user_data['password'],
                    ]);
                }
                
                return app()->handle($authenticate);
                
            }else{
                return response()->json(['errors'=>['password' => 'Password incorrect']],201);
            }
        }else{
            return response()->json(['errors'=>['login' => 'Login incorrect']],201);
        }
        // if(auth()->attempt($credentials)){
                        
        //     $oauth_client=OAuthClient::where('user_id',Auth::id())->first();
        //     //return $oauth_client;
        //     if($oauth_client){
        //         $authenticate = Request::create('/oauth/token', 'POST', [
        //             'grant_type' => 'password',
        //             'client_id' => $oauth_client->id,//env('VUE_CLIENT_ID'),//config('services.vue_client.id'),
        //             'client_secret' => $oauth_client->secret,// env('VUE_CLIENT_SECRET'),// config('services.vue_client.secret'),
        //             'username' => $user_data['login'],
        //             'password' => $user_data['password'],
        //         ]);
        //     }else{
        //         $authenticate = Request::create('/oauth/token', 'POST', [
        //             'grant_type' => 'password',
        //             'client_id' => $client_id,//env('VUE_CLIENT_ID'),//config('services.vue_client.id'),
        //             'client_secret' => $client_secret,// env('VUE_CLIENT_SECRET'),// config('services.vue_client.secret'),
        //             'username' => $user_data['login'],
        //             'password' => $user_data['password'],
        //         ]);
        //     }
            
        //     return app()->handle($authenticate);
        // }else{
        //     return response()->json(['error' => 1, 'message' => 'credential error']);
        // }

    }

    /**
     * Logout user.
     
     * @return mixed
     */
    public function logout()
    {
        $accessToken=$this->getAuth()->token();
        $accessToken->revoke();
        DB::table('oauth_access_tokens')->where('id', $accessToken->id)->delete();
        DB::table('oauth_refresh_tokens')->where('access_token_id', $accessToken->id)->delete();
        
        
    }

    /**
     * Delete a user.
     *
     * @param int
     */
    public function delete($id)
    {
        $this->user->findOrfail($id)->delete();
        
    }

    /**
     * Register a user.
     *
     * @param array
     */
    public function register(array $user_data){
        DB::beginTransaction();
        try {
            try {
                $user = new User();
                $user->nom = $user_data['nom'];
                $user->prenom = $user_data['prenom'];
                $user->adresse = $user_data['adresse'];
                $user->telephone = $user_data['telephone'];
                $user->date_naissance = $user_data['date_naissance'];
                $user->sexe = $user_data['sexe'];
                $user->situation_matrimoniale = $user_data['situation_matrimoniale'];
                $user->email = $user_data['email'];
                $user->login = $user_data['telephone'];
                $user->prospect = $user_data['prospect'];
                $user->actif = $user_data['actif'];
                $user->password = Hash::make($user_data['telephone'].'password');
                $user->userable_id = $user_data['userable_id'];
                $user->userable_type = $user_data['userable_type'];
                $user->commune_id =$user_data['commune'];
    
                $user->save();
            }
            catch(\Exception $e){
                DB::rollback();
                $message = $e->getMessage();
                return response()->json(['errors' => [ 'message' => $message]]);
            }

            $insertedId = $user->id;
	        $secret = str_random(40);
	        try {
		        $oauth_client = OAuthClient::create([
		       		"user_id" => $insertedId,
		       		"secret" => $secret,
		       		"name" => "Password Grant",
		       		"revoked" => 0,
		       		"password_client" => 1,
		       		"personal_access_client" => 0,
					   "redirect" => "http://localhost",
			    ]);
            }
            catch(\Exception $e){
				DB::rollback();
				$message = $e->getMessage();
		    	return response()->json(['errors' => [ 'message' => $message]]);
            }
            DB::commit();   
            return  $user;
        }
        catch(\Exception $e){
            DB::rollback();
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]]);
        }
            
    }

    /**
     * Update a user.
     *
     * @param int
     * @param array
     */
    public function update($id, array $user_data)
    {
            $user =  $this->user->findOrfail($id);
            
            $user->nom = $user_data['nom'];
            $user->prenom = $user_data['prenom'];
            $user->adresse = $user_data['adresse'];
            $user->telephone = $user_data['telephone'];
            $user->date_naissance = $user_data['date_naissance'];
            $user->sexe = $user_data['sexe'];
            $user->situation_matrimoniale = $user_data['situation_matrimoniale'];
            $user->email = $user_data['email'];
            $user->login = $user_data['telephone'];
            $user->prospect = $user_data['prospect'];
            $user->actif = $user_data['actif'];
            $user->password = Hash::make( $user_data['password'] );
            $user->userable_id = $user_data['userable_id'];
            $user->userable_type = $user_data['userable_type'];
            $user->commune_id = $user_data['commune'];
            return $user->update();
    }


    public function getEvolution(){
        return $this->getAuth()->userable->comptes;
    }

    public function getProspects()
    {
        return User::where('prospect',true)->get();
    }

}

