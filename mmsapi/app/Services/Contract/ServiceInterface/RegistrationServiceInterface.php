<?php

namespace App\Services\Contract\ServiceInterface;

use App\Http\Requests\Auth\RegisterRequest;


interface RegistrationServiceInterface
{
    public function register(RegisterRequest $request): array;

}