<?php


namespace App\Services;

use App\Http\Requests\User\RegisterRequest;
use App\Http\Resources\UsersResource;
use App\Repositories\UserRepository;
use App\Services\Contract\AbstractService;
use App\Services\Contract\ServiceInterface\UserServiceInterface;
use App\User;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;

class UserService extends AbstractService implements UserServiceInterface
{
    public function __construct(UserRepository $repository)
    {
        parent::__construct($repository);
    }

    public function getUserByNom($nom)
    {
        return User::where('nom',$nom)->orWhere('prenom',$nom)->paginate();
    }

    public function getUserByPhone($phone)
    {
        return User::where('telephone',$phone)->first();
    }

    public function getAuthenticatedUser()
    {
        return Auth::user();
    }

    public function getDiscussions($user)
    {
        return $this->read($user)->conversations_user;
    }

    public function getNotifications($user)
    {
        return $this->read($user)->conversations_user;
    }

    public function getAuthenticatedUserable()
    {   $user=$this->getAuthenticatedUser();
        if($user){
            return response()->json([ 'success' => ['data' => new UsersResource($user) ]], 200);
        }else{
            return response()->json([ 'errors' => ['message' => 'Aucun résultat']], 400);
        }
    }

    public function index()
    {
       return parent::index();
    }
    public function read($user)
    {
       return parent::read($user);
    }

    public function update(Request $request,$user)
    {
       return parent::update($request,$user);
    }

    public function delete($user)
    {
       return parent::delete($user);
    }
}