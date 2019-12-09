<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface PortefeuilleServiceInterface
{
    public function index();
    public function read($portefeuille);
    public function create(Request $request);
    public function delete($portefeuille);
    public function update(Request $request, $portefeuille);
}