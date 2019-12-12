<?php

namespace App\Services\Contract\ServiceInterface;

use Illuminate\Http\Request;
use Illuminate\Http\Response;

interface ConversationServiceInterface
{
    public function index();
    public function read($conversation);
    public function create(Request $request);
    public function delete($conversation);
    public function update(Request $request, $conversation);
}