<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface AssurerServiceInterface
{
    public function index();
    public function read($assurer);
    public function create(Request $request);
    public function delete($assurer);
    public function update(Request $request, $assurer);
}