<?php

namespace App\Services\Contract\ServiceInterface;

use App\Http\Requests\User\RegisterRequest;
use Illuminate\Http\Request;

interface RegistrationServiceInterface
{
    public function register(Request $request);

}