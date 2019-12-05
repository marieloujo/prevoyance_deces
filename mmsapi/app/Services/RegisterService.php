<?php

namespace App\Services;


use App\Http\Requests\Auth\RegisterRequest;
use App\Repositories\Contracts\RepositoryInterface;
use App\Services\Contract\ServiceInterface\JWTServiceInterface;
use App\Services\Contract\ServiceInterface\RegistrationServiceInterface;
use Illuminate\Support\Facades\Hash;

class RegisterService implements RegistrationServiceInterface
{
    protected $repository;

    public function __construct(RepositoryInterface $repository)
    {
        $this->repository = $repository;
    }

    public function register(RegisterRequest $request): array
    {
        $user = $request->user;
        $user['password'] = Hash::make($user['password']);
        $user = $this->repository->create($user);
        return $user->id
            ? ['token' => $user->createToken('AuthToken')->accessToken,
                'userId' => $user->id,
                'status' => 'success',
                'code' => 200]
            : ['status' => 'error',
                'message' => 'Registration error occurs!',
                'code' => 422];
    }
}