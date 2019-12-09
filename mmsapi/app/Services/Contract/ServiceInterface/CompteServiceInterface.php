<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request ;
use Illuminate\Http\Response;

interface CompteServiceInterface
{
    public function index();
    public function read($compte);
    public function create(Request $request);
    public function delete($compte);
    public function update(Request $request, $compte);
}