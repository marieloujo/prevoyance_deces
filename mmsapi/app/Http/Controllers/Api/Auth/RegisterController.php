<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use App\Http\Requests\User\RegisterRequest;
use Illuminate\Http\Request;

use App\Services\Contract\ServiceInterface\RegistrationServiceInterface;
class RegisterController extends Controller
{
    
    protected $registerService;

    public function __construct(RegistrationServiceInterface $registerService)
    {
        $this->registerService = $registerService;
    }

    public function register(RegisterRequest $request)
    {
        $result = $this->registerService->register($request);
        return $result;
    }
}
