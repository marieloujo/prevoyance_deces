<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Response;

interface JWTServiceInterface
{
    public function validateToken(): array;

    public function createTokenByCredentials(string $identity, string $password): Response;
}