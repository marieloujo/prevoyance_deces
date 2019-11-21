<?php

namespace App\Repositories\User;

use App\Models\Image;
use App\User;
use Illuminate\Http\Request;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Illuminate\Support\Facades\Auth;

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

    /**
     * Get a user by it's ID
     *
     * @param string
     * @return collection
     */

    public function getByName($name)
    {
        return $this->user->where('nom','=',$name)->get();
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
    public function login(array $user_data)
    {
        $credentials = Request::create('/oauth/token', 'POST', [
            'grant_type' => 'password',
            'client_id' => 2 ,//env('VUE_CLIENT_ID'),//config('services.vue_client.id'),
            'client_secret' => 'La7NqEiQRe9lfI2vWgOO9bTkm4fczQuiwu5KXfZg',// env('VUE_CLIENT_SECRET'),// config('services.vue_client.secret'),
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
        $accessToken = auth()->user()->token();
        DB::table('oauth_access_tokens')->where('id', $accessToken)->delete();
        DB::table('oauth_refresh_tokens')->where('access_token_id', $accessToken)->delete();
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
    public function register(array $user_data)
    {
            $user = new User();
            $user->name = $user_data['name'];
            $user->telephone = $user_data['telephone'];
            $user->role_id = 1;
            $p=$user_data['password'];
            $user->password = bcrypt( $user_data['password'] );
            $user->save();

            return $this->login($user_data);
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
            $user->name = $user_data['name'];
            $user->telephone = $user_data['telephone'];
            $user->password = bcrypt( $user_data['password'] );
            $user->update();
    }

}

