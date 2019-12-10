<?php

namespace App\Repositories\User;

use App\Models\Commune;
use App\User;
use Illuminate\Http\Request;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Illuminate\Support\Facades\Auth;
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
        $credentials = Request::create('/oauth/token', 'POST', [
            'grant_type' => 'password',
            'client_id' => $client_id,//env('VUE_CLIENT_ID'),//config('services.vue_client.id'),
            'client_secret' => $client_secret,// env('VUE_CLIENT_SECRET'),// config('services.vue_client.secret'),
            'username' => $user_data['login'],
            'password' => $user_data['password'],
        ]);
        
        return app()->handle($credentials);
    }

    /**
     * Logout user.
     
     * @return mixed
     */
    public function logout()
    {
        return $this->getAuth()->token()->revoke();
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
            $user->usereable_id = $user_data['usereable_id'];
            $user->usereable_type = $user_data['usereable_type'];
            $user->commune_id =$user_data['commune'];

            $user->save();
            return $user;
            
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
            $user->password = bcrypt( $user_data['password'] );
            $user->usereable_id = $user_data['usereable_id'];
            $user->usereable_type = $user_data['usereable_type'];
            $user->commune_id = $user_data['commune'];
            return $user->update();
    }


    public function getEvolution(){
        return $this->getAuth()->usereable->comptes;
    }
}

