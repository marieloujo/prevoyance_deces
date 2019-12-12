<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface BeneficeServiceInterface
{
    public function index();
    public function read($benefice);
    public function create(Request $request);
    public function delete($benefice);
    public function update(Request $request, $benefice);
}