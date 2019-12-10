<?php

namespace App\Http\Controllers\Api\Auth;

use App\Http\Controllers\Controller;
use App\Services\Contracts\Service\JWTServiceInterface;
use Illuminate\Http\Request;

class OauthController extends Controller
{
    protected $jwtService;

    public function __construct(JWTServiceInterface $jwtService)
    {
        $this->jwtService = $jwtService;
    }

    public function validateToken(Request $request)
    {
        return response()
            ->json($this->jwtService
                ->validateToken()
            );
    }
}
