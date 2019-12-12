<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface MessageServiceInterface
{
    public function index();
    public function read($message);
    public function create(Request $request);
    public function delete($message);
    public function update(Request $request, $message);
}