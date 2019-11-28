<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Requests\RegisterRequest;
use App\Http\Requests\User\LoginRequest;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use http\Client\Response;
use Illuminate\Http\Request;

class UserController extends Controller
{
    protected $user_repository;
    public function __construct(UserRepositoryInterface $userRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;
    }

    public function login(LoginRequest $request)
    {
        return $this->user_repository->login($request->request->all());
    }

    public function logout()
    {
        $this->user_repository->logout();

        return response()->json([
            "status" => " success"
        ],Response::HTTP_NO_CONTENT);
    }

    public function register(RegisterRequest $request)
    {
        return $this->user_repository->register($request->request->all());

    }
}
