<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface DirectionServiceInterface
{
    public function index();
    public function read($direction);
    public function create(Request $request);
    public function delete($direction);
    public function update(Request $request, $direction);
}