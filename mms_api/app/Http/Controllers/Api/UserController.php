<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\User\RegisterRequest;
use App\Http\Requests\User\LoginRequest;
use App\Http\Resources\Client\ClientResource;
use App\Http\Resources\CompteResource;
use App\Http\Resources\Conversation\MessageResource;
use App\Http\Resources\DirectionResource;
use App\Http\Resources\Marchand\MarchandResource;
use App\Http\Resources\SuperMarchand\SuperMarchandResource;
use App\Http\Resources\UserResource;
use App\Http\Resources\UsersResource;
use App\Repositories\Message\Interfaces\MessageRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Auth;
use Symfony\Component\HttpFoundation\Response;

class UserController extends Controller
{
    
    protected $user_repository;
    public function __construct(UserRepositoryInterface $userRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
    }

    public function getUser(){
        return $this->user_repository->getAuth()->usereable();
    }

    public function user()
    {   
        //$user=$this->user_repository->getAuth();
        //return $user->with(['commune','usereable'])->first();

        return new UsersResource($this->user_repository->getAuth());
        $user= $this->user_repository->getAuth();

        if($user['usereable_type']=='App\\Models\\SuperMarchand'){
            return new SuperMarchandResource($user->usereable);
        }
        if($user['usereable_type']=='App\\Models\\Marchand'){
            return new MarchandResource(($user->usereable));
        }
        if($user['usereable_type']=='App\\Models\\Client'){
            return new ClientResource($user->usereable);
        }
        if($user['usereable_type']=='App\\Models\\Direction'){
            return new DirectionResource($user->usereable);
        }

    }

    public function login(LoginRequest $request)
    { 
        return $this->user_repository->login($request->all(),config('services.vue_client.id'),config('services.vue_client.secret'));
    }


    public function create(RegisterRequest $request)
    {          
        return $this->user_repository->register($request->all());
    }

    public function logout()
    {
        if($this->user_repository->logout()){
            return response()->json([],Response::HTTP_NO_CONTENT);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  string  $client
     * @return \Illuminate\Http\Response
     */
    public function showByName($name)
    { 
        return UsersResource::collection($this->user_repository->getByName($name));
    }
    
    /**
     * Display the specified resource.
     *
     * @param  string  $client
     * @return \Illuminate\Http\Response
     */
    public function showByPhone($phone)
    {
        return new UsersResource($this->user_repository->getByPhone($phone));
    }

    public function statistique()
    {//return $this->user_repository->getEvolution();
        return CompteResource::collection($this->user_repository->getEvolution());
    }

/*     public function conversations()
    {  return Auth::user()->messages;
        return $this->message_repository->getAuthMessage($this->user_repository->getAuth()->id);
        return CompteResource::collection($this->user_repository->getEvolution());
    } */
    public function update(RegisterRequest $request, $id)
    {
        $this->user_repository->update($id,$request->all());
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $this->user_repository->delete($id);
    }

}
