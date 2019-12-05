<?php

namespace App\Http\Controllers\Api;

use Illuminate\Http\Request;
use App\Http\Controllers\Controller;
use App\Http\Resources\Conversation\MessageResource as Conversation;
use App\Http\Resources\MessageResource;
use App\Repositories\Message\Interfaces\MessageRepositoryInterface;
use App\Repositories\User\Interfaces\UserRepositoryInterface;
use Illuminate\Support\Facades\Auth;

class MessageController extends Controller
{
    protected $user_repository;
    protected $message_repository;
    public function __construct(UserRepositoryInterface $userRepositoryInterface,MessageRepositoryInterface $messageRepositoryInterface)
    {
        $this->user_repository=$userRepositoryInterface;    
        $this->message_repository=$messageRepositoryInterface;    
    }

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request)
    {
        //
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($id)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id)
    {
        //
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        //
    }

    public function notifications()
    {   
        //return $this->message_repository->getAuthNotification($this->user_repository->getAuth()->id);
        return MessageResource::collection($this->message_repository->getAuthNotification($this->user_repository->getAuth()->id));
    }

    public function conversations()
    {  return Auth::user()->messages;
        return $this->message_repository->getAuthMessage($this->user_repository->getAuth()->id);
       // return CompteResource::collection($this->user_repository->getEvolution());
    }

}
