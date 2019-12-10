<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Services\Contract\ServiceInterface\UserServiceInterface;
use Illuminate\Http\JsonResponse;
use Illuminate\Http\Request;

class UserController extends Controller
{
    
    protected $userService;


    public function __construct(UserServiceInterface $userService)
    {
        $this->userService = $userService;
    }

    public function getAuthenticatedUser(): JsonResponse
    {
        $result = $this->userService->getAuthenticatedUser();
        return response()->json($result, $result['code']);
    }

    public function all(){
        return $this->userService->index();
    }
}
