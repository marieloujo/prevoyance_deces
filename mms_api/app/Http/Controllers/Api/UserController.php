<?php

namespace App\Http\Controllers\Api;
use App\User;
use App\Http\Controllers\Controller;
use App\Http\Requests\RegisterRequest;
use App\Http\Requests\User\LoginRequest;
use App\Http\Resources\Marchand\MarchandResource;
use App\Http\Resources\Souscripteur\ClientResource;
use App\Http\Resources\SuperMarchand\SuperMarchandResource;
use App\Http\Resources\UserResources;
use App\Http\Resources\UsersResource;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use http\Client\Response;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;
use Illuminate\Support\Facades\Auth;
class UserController extends Controller
{

    protected $user_repository;
    public function __construct(UserRepositoryInterface $userRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;
    }


    public function user()
    {
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

    }

    public function login(LoginRequest $request)
            return $this->user_repository->login($request->request->all(),config('services.vue_client.id'),config('services.vue_client.secret'));
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
    public function showByName($client)
    {
        return UsersResource::collection($this->user_repository->getByName($client));
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
    {
        return $this->user_repository->getEvolution();
        return new UsersResource($this->user_repository->getEvolution());
    }


}
