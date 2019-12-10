<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Response;

interface UserServiceInterface
{
    public function getAuthenticatedUser(): array;
    public function index();
}