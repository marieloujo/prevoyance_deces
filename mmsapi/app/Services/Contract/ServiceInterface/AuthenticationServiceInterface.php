<?php

namespace App\Services\Contract\ServiceInterface;

use App\Http\Requests\Auth\LoginRequest;


interface AuthenticationServiceInterface
{
    public function login(LoginRequest $request): array;

    public function logout(): array;
}