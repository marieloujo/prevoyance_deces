<?php

namespace App\Http\Controllers\Api;

use App\Http\Controllers\Controller;
use App\Http\Resources\Collection\ConversationUsersResource;
use App\Services\Contract\ServiceInterface\ConversationUserServiceInterface;
use Illuminate\Http\Request;
use Symfony\Component\HttpFoundation\Response;

class ConversationUserController extends Controller
{
    protected $conversationUserService;

    public function __construct(ConversationUserServiceInterface $conversationUserService)
    {
        $this->conversationUserService = $conversationUserService;
    } 

    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index()
    {
        return $conversationUserData=$this->conversationUserService->index();
        if(count($conversationUserData)>0){
            return ConversationUsersResource::collection($conversationUserData);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun conversationUser' ]], Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */
    public function show($conversationUser)
    {
        $conversationUserData=$this->conversationUserService->read($conversationUser);
        if($conversationUserData){
            return response()->json([ 'success' => ['data' => new conversationUsersResource($conversationUserData) ]], Response::HTTP_OK);
        }else{
            return response()->json([ 'success' => ['message' => 'Aucun conversationUser' ]], Response::HTTP_NOT_FOUND);
        }
    }

    public function store(Request $request)
    {
        try {         
            return response()->json([ 'success' => ['data' => new conversationUsersResource( $this->conversationUserService->create($request)) ]], Response::HTTP_OK);
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_NOT_FOUND);
        }
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  int  $id
     * @return \Illuminate\Http\Response
     */


    public function update(Request $request, $conversationUser){
        
        try {         
            if($this->conversationUserService->update($request,$conversationUser)){
                return $this->show($conversationUser);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le conversationUser n\'a pas été mis à jour'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }

    public function destroy($conversationUser){
        try {       
            if($this->conversationUserService->delete($conversationUser)){
                return response()->json([ 'success' => ['message' => 'Le conversationUser à bien été supprimé' ]], Response::HTTP_OK);
            }else{
                return response()->json([ 'errors' => ['message' =>  'Le conversationUser n\'a pas été supprimé'  ]], Response::HTTP_INTERNAL_SERVER_ERROR);
            }  
        } catch (\Exception $e) {
            $message = $e->getMessage();
            return response()->json(['errors' => [ 'message' => $message]],Response::HTTP_INTERNAL_SERVER_ERROR);
        }
    }
}
