<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface BeneficiaireServiceInterface
{
    public function index();
    public function read($beneficiaire);
    public function create(Request $request);
    public function delete($beneficiaire);
    public function update(Request $request, $beneficiaire);
}