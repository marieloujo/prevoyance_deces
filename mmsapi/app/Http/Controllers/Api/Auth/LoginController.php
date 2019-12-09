<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use App\Http\Requests\User\LoginRequest;
use Illuminate\Http\Request;

use App\Services\Contract\ServiceInterface\AuthenticationServiceInterface;
use App\User;

class LoginController extends Controller
{
    
    protected $loginService;


    public function __construct(AuthenticationServiceInterface $loginService)
    {
        $this->loginService = $loginService;
    }

    public function login(LoginRequest $request)
    {
        return $this->loginService->login($request);
        //return $result;
    }

    public function logout()
    {
        $result = $this->loginService->logout();
        return $result;
    }
}
