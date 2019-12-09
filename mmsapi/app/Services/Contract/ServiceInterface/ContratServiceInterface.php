<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface ContratServiceInterface
{
    public function index();
    public function read($contrat);
    public function create(Request $request);
    public function delete($contrat);
    public function update(Request $request, $contrat);
}