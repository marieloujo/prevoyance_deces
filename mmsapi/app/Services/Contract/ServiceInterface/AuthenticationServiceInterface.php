<?php

namespace App\Services\Contract\ServiceInterface;

use App\Http\Requests\User\LoginRequest;
use App\Http\Requests\User\RegisterRequest;

interface AuthenticationServiceInterface
{
    public function createTokenByCredentials($id, array $request);
    
    public function login(LoginRequest $request);

    public function logout();
}